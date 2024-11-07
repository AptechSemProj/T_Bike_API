package se.pj.tbike.core.util;

import org.springframework.data.jpa.repository.JpaRepository;
import se.pj.tbike.caching.Cache;
import se.pj.tbike.core.common.entity.IdentifiedEntity;
import se.pj.tbike.service.CrudService;
import se.pj.tbike.util.Output;
import se.pj.tbike.caching.CacheManager;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class SimpleCacheableService<
		E extends IdentifiedEntity<E, K> & Cacheable<E>,
		K extends Comparable<K>,
		R extends JpaRepository<E, K>
		> extends SimpleCrudService<E, K, R>
		implements CrudService<E, K> {

	private final AtomicLong totalElements;
	private final CacheManager<K> manager;
	private final Supplier<E> instanceSupplier;

	public SimpleCacheableService(R repository,
	                              Supplier<E> instanceSupplier,
	                              Function<E, K> keyProvider) {
		super( repository );
		this.manager = createCacheManager();
		long count = repository.count();
		this.totalElements = new AtomicLong( count );
		this.instanceSupplier = instanceSupplier;
	}

	protected abstract CacheManager<K> createCacheManager();

	public final long getTotalElements() {
		return totalElements.get();
	}

	public final CacheManager<K> getCacheManager() {
		return manager;
	}

	protected final E createEntityFromCache(Cache c) {
		return instanceSupplier.get().fromCacheObject( c.get() );
	}

	public final boolean isFullCaching() {
		return manager.size() == getTotalElements();
	}

	@Override
	public Output.Array<E> findAll() {
		if ( getTotalElements() == manager.size() ) {
			List<E> list = manager.stream()
					.map( this::createEntityFromCache )
					.toList();
			return Output.array( list );
		}
		Output.Array<E> arr = super.findAll();
		arr.forEach( e -> manager.cache( e.getId(), e.toCacheObject() ) );
		return arr;
	}

	protected final Output.Pagination<E> getPageFromCache(int num, int size) {
		if ( num < 0 ) {
			throw new IllegalArgumentException();
		}
		if ( size < 0 ) {
			throw new IllegalArgumentException();
		}
		long totalElements = this.getTotalElements();
		int cacheSize = this.manager.size();
		int offset = num * size, to = offset + size;
		if ( offset >= cacheSize ) {
			return null;
		}
		int length;
		if ( to <= cacheSize ) {
			length = size;
		} else if ( cacheSize == totalElements ) {
			length = cacheSize - offset;
		} else {
			return null;
		}
		List<E> content = this.manager.stream().parallel()
				.skip( offset ).limit( length )
				.map( this::createEntityFromCache )
				.toList();
		return Output.pagination( content, num, size, totalElements );
	}

	@Override
	public Output.Pagination<E> findPage(int num, int size) {
		Output.Pagination<E> page = getPageFromCache( num, size );
		if ( page != null ) {
			return page;
		}
		Output.Pagination<E> arr = super.findPage( num, size );
		arr.forEach( e -> manager.cache( e.getId(), e.toCacheObject() ) );
		return arr;
	}

	@Override
	public Output.Value<E> findByKey(K id) {
		if ( id == null ) {
			throw new IllegalArgumentException();
		}
		Cache cache = manager.get( id );
		if ( cache.isPresent() ) {
			return Output.value( createEntityFromCache( cache ) );
		}
		Output.Value<E> r = super.findByKey( id );
		manager.cache( id, r.isNull() ? null : r.get().toCacheObject() );
		return r;
	}

	@Override
	public boolean existsByKey(K id) {
		if ( id == null ) {
			throw new IllegalArgumentException();
		}
		if ( manager.isCaching( id ) ) {
			return true;
		}
		if ( getRepository().existsById( id ) ) {
			manager.cache( id, null );
			return true;
		}
		return false;
	}

	@Override
	public E create(E e) {
		E created = super.create( e );
		manager.cache( created.getId(), created.toCacheObject() );
		totalElements.incrementAndGet();
		return created;
	}

	@Override
	public boolean update(E newVal, E oldVal) {
		boolean isUpdated = super.update( newVal, oldVal );
		if ( isUpdated ) {
			manager.set( newVal.getId(), newVal.toCacheObject() );
		}
		return isUpdated;
	}

	@Override
	public boolean remove(E e) {
		boolean isDeleted = super.remove( e );
		if ( isDeleted ) {
			manager.remove( e.getId() );
			totalElements.decrementAndGet();
		}
		return isDeleted;
	}

	@Override
	public boolean removeByKey(K id) {
		boolean isDeleted = super.removeByKey( id );
		if ( isDeleted ) {
			manager.remove( id );
			totalElements.decrementAndGet();
		}
		return isDeleted;
	}
}

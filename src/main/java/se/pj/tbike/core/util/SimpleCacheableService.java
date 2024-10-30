package se.pj.tbike.core.util;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import se.pj.tbike.caching.CachedData;
import se.pj.tbike.service.CrudService;
import se.pj.tbike.util.Output;
import se.pj.tbike.caching.CacheManager;
import se.pj.tbike.caching.Cacheable;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public abstract class
SimpleCacheableService<E extends Cacheable, K extends Comparable<K>>
		extends SimpleCrudService<E, K>
		implements CrudService<E, K> {

	private final AtomicLong elementCount;
	private final CacheManager<K, E> manager;
	private final JpaRepository<E, K> repository;

	public SimpleCacheableService(JpaRepository<E, K> repository,
	                              Function<E, K> keyProvider) {
		super( repository, keyProvider );
		this.repository = getRepository();
		this.elementCount = new AtomicLong( repository.count() );
		this.manager = getCacheManager();
	}

	protected abstract CacheManager<K, E> getCacheManager();

	@Override
	public Output.Array<E> findAll() {
		if ( elementCount.get() == manager.size() ) {
			List<E> list = manager.stream()
					.toList();
			return Output.array( list );
		}
		Output.Array<E> arr = super.findAll();
		arr.forEach( e -> manager.cache( e ) );
		return arr;
	}

	@Override
	public Output.Array<E> findAll(Sort sort) {
		return super.findAll( sort );
	}

	@Override
	public Output.Pagination<E> findPage(int num, int size) {
		if ( num < 0 ) {
			throw new IllegalArgumentException();
		}
		if ( size < 0 ) {
			throw new IllegalArgumentException();
		}
		long count = elementCount.get();
		int cacheSize = manager.size();
		int offset = num * size, to = offset + size;
		get_from_cache:
		{
			if ( offset < cacheSize ) {
				int length;
				if ( to < cacheSize ) {
					length = size;
				} else if ( cacheSize == count ) {
					length = cacheSize - offset;
				} else {
					break get_from_cache;
				}
				List<E> s = manager.stream()
						.parallel()
						.skip( offset )
						.limit( length )
						.toList();
				return Output.pagination( s, num, size, count );
			}
		}
		Output.Pagination<E> arr = super.findPage( num, size );
		arr.forEach( e -> manager.cache( e ) );
		return arr;
	}

	@Override
	public Output.Value<E> findByKey(K id) {
		if ( id == null ) {
			throw new IllegalArgumentException();
		}
		CachedData<E> cache = manager.get( id );
		if ( cache.isPresent() ) {
			return Output.value( cache.get() );
		}
		Output.Value<E> r = super.findByKey( id );
		manager.cache( id, r.isNull() ? null : r.get() );
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
		Optional<E> o = repository.findById( id );
		manager.cache( id, o.orElse( null ) );
		return o.isPresent();
	}

	@Override
	public E create(E e) {
		E created = super.create( e );
		manager.cache( created );
		elementCount.incrementAndGet();
		return created;
	}

	@Override
	public boolean update(E newVal, E oldVal) {
		boolean isUpdated = super.update( newVal, oldVal );
		if ( isUpdated ) {
			manager.update( newVal );
		}
		return isUpdated;
	}

	@Override
	public boolean remove(E e) {
		boolean isDeleted = super.remove( e );
		if ( isDeleted ) {
			manager.remove( e );
			elementCount.decrementAndGet();
		}
		return isDeleted;
	}

	@Override
	public boolean removeByKey(K id) {
		boolean isDeleted = super.removeByKey( id );
		if ( isDeleted ) {
			manager.remove( id );
			elementCount.decrementAndGet();
		}
		return isDeleted;
	}
}

package se.pj.tbike.core.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import se.pj.tbike.caching.CacheManager;
import se.pj.tbike.caching.Cacheable;
import se.pj.tbike.caching.CachedData;
import se.pj.tbike.core.common.SoftDeletionEntity;
import se.pj.tbike.core.common.SoftDeletionRepository;
import se.pj.tbike.service.CrudService;
import se.pj.tbike.util.Output;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public abstract class
SoftDeletionCacheableService<
		E extends SoftDeletionEntity<E> & Cacheable,
		K extends Comparable<K>,
		R extends JpaRepository<E, K> & SoftDeletionRepository<E, K>
		>
		extends SimpleCrudService<E, K>
		implements CrudService<E, K> {

	private final AtomicLong count;
	private final CacheManager<K, E> manager;
	private final R repository;

	public SoftDeletionCacheableService(R repository,
	                                    Function<E, K> keyProvider) {
		super( repository, keyProvider );
		this.repository = repository;
		this.count = new AtomicLong( repository.countByDeletedFalse() );
		this.manager = getCacheManager();
	}

	protected abstract CacheManager<K, E> getCacheManager();

	@Override
	public Output.Array<E> findAll() {
		if ( count.get() == manager.size() ) {
			List<E> list = manager.stream()
					.toList();
			return Output.array( list );
		}
		List<E> list = repository.findByDeletedFalse();
		Output.Array<E> arr = Output.array( list );
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
		long count = this.count.get();
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
		PageRequest req = PageRequest.of( num, size );
		Page<E> page = repository.findByDeletedFalse( req );
		page.getContent().forEach( manager::cache );
		return Output.pagination(
				page.getContent(),
				page.getNumber(),
				page.getSize(),
				page.getTotalElements(),
				page.getTotalPages()
		);
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
		if ( r.isNull() ) {
			manager.cache( id, null );
		} else {
			E e = r.get();
			if ( e.isDeleted() ) {
				manager.cache( id, null );
				return Output.value( null );
			} else {
				manager.cache( e );
			}
		}
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
		if ( o.isPresent() ) {
			E e = o.get();
			if ( e.isDeleted() ) {
				manager.cache( id, null );
				return false;
			} else {
				manager.cache( e );
				return true;
			}
		}
		return false;
	}

	@Override
	public E create(E e) {
		E created = super.create( e );
		manager.cache( created );
		count.incrementAndGet();
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
		e.setDeleted( true );
		boolean isDeleted = super.update( e );
		if ( isDeleted ) {
			manager.remove( e );
			count.decrementAndGet();
		}
		return isDeleted;
	}

	@Override
	public boolean removeByKey(K id) {
		Optional<E> o = repository.findById( id );
		return o.filter( this::remove ).isPresent();
	}
}

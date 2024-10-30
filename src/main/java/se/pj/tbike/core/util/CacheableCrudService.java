package se.pj.tbike.core.util;

import java.util.List;
import java.util.Optional;

import java.util.function.Function;

import org.springframework.data.jpa.repository.JpaRepository;

import se.pj.tbike.service.CrudService;
import se.pj.tbike.util.Output;

import se.pj.tbike.util.cache.Storage;

@Deprecated
public abstract class CacheableCrudService<T, K extends Comparable<K>>
		extends SimpleCrudService<T, K>
		implements CrudService<T, K> {

	public CacheableCrudService(JpaRepository<T, K> repository,
	                            Function<T, K> keyProvider) {
		super( repository, keyProvider );
	}

	protected abstract Storage<K, T> getStorage();

	@Override
	public Output.Array<T> findAll() {
		Storage<K, T> storage = getStorage();
		return Output.array( storage.values() );
	}

	@Override
	public Output.Pagination<T> findPage(int num, int size) {
		Storage<K, T> storage = getStorage();
		int offset = num * size, to = offset + size;
		List<T> b = storage.valuesBetween( offset, to );
		return Output.pagination( b, num, size, storage.getRealSize() );
	}

	@Override
	public Output.Value<T> findByKey(K id) {
		Storage<K, T> storage = getStorage();
		if ( storage.isCaching( id ) ) {
			Optional<T> b = storage.get( id );
			return Output.value( b.orElse( null ) );
		}
		Output.Value<T> r = super.findByKey( id );
		storage.cache( id, r.isNull() ? null : r.get() );
		return r;
	}

	@Override
	public boolean existsByKey(K id) {
		return getStorage().isCaching( id ) || super.existsByKey( id );
	}

	@Override
	public T create(T t) {
		T created = super.create( t );
		getStorage().cache( created );
		return created;
	}

	@Override
	public boolean update(T newVal, T oldVal) {
		boolean isUpdated = super.update( newVal, oldVal );
		if ( isUpdated ) getStorage().cache( newVal );
		return isUpdated;
	}

	@Override
	public boolean remove(T t) {
		boolean isDeleted = super.remove( t );
		if ( isDeleted ) getStorage().remove( t );
		return isDeleted;
	}

	@Override
	public boolean removeByKey(K id) {
		boolean isDeleted = super.removeByKey( id );
		if ( isDeleted ) getStorage().remove( id );
		return isDeleted;
	}
}

package se.pj.tbike.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.jpa.repository.JpaRepository;

import se.pj.tbike.util.cache.Storage;

import se.pj.tbike.util.result.Result;
import se.pj.tbike.util.result.ResultImpl;
import se.pj.tbike.util.result.ResultList;
import se.pj.tbike.util.result.ResultListImpl;
import se.pj.tbike.util.result.ResultPage;
import se.pj.tbike.util.result.ResultPageImpl;

public abstract class CacheableCrudService<T, K extends Comparable<K>>
		extends StdCrudService<T, K>
		implements CrudService<T, K> {

	public CacheableCrudService( JpaRepository<T, K> repository,
	                             Function<T, K> keyProvider ) {
		super( repository, keyProvider );
	}

	protected abstract Storage<K, T> getStorage();

	@Override
	public ResultList<T> findAll() {
		Storage<K, T> storage = getStorage();
		return new ResultListImpl<>( storage.values() );
	}

	@Override
	public ResultPage<T> findPage( int num, int size ) {
		Storage<K, T> storage = getStorage();
		int offset = num * size, to = offset + size;
		List<T> b = storage.valuesBetween( offset, to );
		return new ResultPageImpl<>( b, num, storage.getRealSize(), size );
	}

	@Override
	public Result<T> findByKey( K id ) {
		Storage<K, T> storage = getStorage();
		if ( storage.isCaching( id ) ) {
			Optional<T> b = storage.get( id );
			return new ResultImpl<>( b.orElse( null ) );
		}
		Result<T> r = super.findByKey( id );
		storage.cache( id, r.isEmpty() ? null : r.get() );
		return r;
	}

	@Override
	public boolean exists( K id ) {
		return getStorage().isCaching( id ) || super.exists( id );
	}

	@Override
	public T create( T t ) {
		T created = super.create( t );
		getStorage().cache( created );
		return created;
	}

	@Override
	public boolean update( T newVal, T oldVal ) {
		boolean isUpdated = super.update( newVal, oldVal );
		if ( isUpdated ) getStorage().cache( newVal );
		return isUpdated;
	}

	@Override
	public boolean remove( T t ) {
		boolean isDeleted = super.remove( t );
		if ( isDeleted ) getStorage().remove( t );
		return isDeleted;
	}

	@Override
	public boolean remove( K id ) {
		boolean isDeleted = super.remove( id );
		if ( isDeleted ) getStorage().remove( id );
		return isDeleted;
	}
}

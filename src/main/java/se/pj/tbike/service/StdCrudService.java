package se.pj.tbike.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import se.pj.tbike.util.result.ResultPage;
import se.pj.tbike.util.result.Result;
import se.pj.tbike.util.result.ResultList;
import se.pj.tbike.util.result.ResultListImpl;
import se.pj.tbike.util.result.ResultPageImpl;
import se.pj.tbike.util.result.ResultImpl;

public class StdCrudService<T, K extends Comparable<K>>
		implements CrudService<T, K> {

	private final JpaRepository<T, K> repository;
	private final Function<T, K> keyProvider;

	public StdCrudService( JpaRepository<T, K> repository,
	                       Function<T, K> keyProvider ) {
		this.repository = repository;
		this.keyProvider = keyProvider;
	}

	@Override
	public final JpaRepository<T, K> getRepository() {
		return repository;
	}

	@Override
	public ResultList<T> findAll() {
		return findAll( Sort.unsorted() );
	}

	@Override
	public ResultList<T> findAll( Sort sort ) {
		List<T> list = repository.findAll( sort );
		return new ResultListImpl<>( list );
	}

	@Override
	public ResultPage<T> findPage( int num, int size ) {
		PageRequest req = PageRequest.of( num, size );
		Page<T> page = repository.findAll( req );
		return new ResultPageImpl<>( page );
	}

	@Override
	public Result<T> findByKey( K id ) {
		Optional<T> o = repository.findById( id );
		return new ResultImpl<>( o.orElse( null ) );
	}

	@Override
	public boolean exists( T t ) {
		return exists( keyProvider.apply( t ) );
	}

	@Override
	public boolean exists( K id ) {
		return repository.existsById( id );
	}

	@Override
	public T create( T t ) {
		return repository.save( t );
	}

	@Override
	public boolean update( T newVal, T oldVal ) {
		if ( oldVal != null && !exists( oldVal ) )
			return false;
		repository.save( newVal );
		return true;
	}

	@Override
	public boolean update( T t ) {
		return update( t, null );
	}

	@Override
	public boolean remove( K id ) {
		repository.deleteById( id );
		return true;
	}

	@Override
	public boolean remove( T t ) {
		repository.delete( t );
		return true;
	}

	@Override
	public boolean remove( K id, T t ) {
		if ( id == null )
			throw new NullPointerException();
		if ( t == null )
			throw new NullPointerException();
		if ( id != keyProvider.apply( t ) )
			return false;
		return remove( t );
	}
}

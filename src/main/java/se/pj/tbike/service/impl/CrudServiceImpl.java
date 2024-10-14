package se.pj.tbike.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import se.pj.tbike.util.result.impl.ResultListImpl;
import se.pj.tbike.util.result.impl.ResultPageImpl;
import se.pj.tbike.util.result.impl.ResultImpl;
import se.pj.tbike.util.cache.Storage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import se.pj.tbike.util.result.ResultPage;
import se.pj.tbike.util.result.Result;
import se.pj.tbike.util.result.ResultList;
import se.pj.tbike.service.CrudService;

public class CrudServiceImpl<T, K extends Comparable<K>>
		implements CrudService<T, K> {

	private final JpaRepository<T, K> repository;
	private final Function<T, K> keyProvider;
	protected final Storage<K, T> storage;

	public CrudServiceImpl( JpaRepository<T, K> repository,
	                        Function<T, K> keyProvider,
	                        Storage<K, T> storage ) {
		this.repository = repository;
		this.keyProvider = keyProvider;
		this.storage = storage;
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
		list.forEach( storage::cache );
		return new ResultListImpl<>( list );
	}

	@Override
	public ResultPage<T> findPage( int num, int size ) {
		PageRequest req = PageRequest.of( num, size );
		Page<T> page = repository.findAll( req );
		page.getContent().forEach( storage::cache );
		return new ResultPageImpl<>( page );
	}

	@Override
	public Result<T> findByKey( K id ) {
		Optional<T> o;
		if ( ( o = storage.get( id ) ).isPresent() )
			return new ResultImpl<>( o.get() );
		o = repository.findById( id );
		o.ifPresent( storage::cache );
		return new ResultImpl<>( o.orElse( null ) );
	}

	@Override
	public boolean exists( T t ) {
		return exists( keyProvider.apply( t ) );
	}

	@Override
	public boolean exists( K id ) {
		return storage.isCaching( id ) || repository.existsById( id );
	}

	@Override
	public T create( T t ) {
		T saved = repository.save( t );
		return storage.cache( saved ).orElseThrow();
	}

	@Override
	public boolean update( T newVal, T oldVal ) {
		if ( oldVal != null && oldVal.equals( newVal ) )
			return true;
		T saved = repository.save( newVal );
		storage.cache( saved );
		return storage.isCaching( saved );
	}

	@Override
	public boolean update( T t ) {
		return update( t, null );
	}

	@Override
	public boolean remove( K id ) {
		repository.deleteById( id );
		storage.remove( id );
		return true;
	}

	@Override
	public boolean remove( T t ) {
		repository.delete( t );
		storage.remove( t );
		return true;
	}

	@Override
	public boolean remove( K id, T t ) {
		if ( id == null )
			throw new NullPointerException();
		if ( t == null )
			throw new NullPointerException();
		if ( !Objects.equals( id, keyProvider.apply( t ) ) )
			return false;
		return remove( t );
	}
}

package se.pj.tbike.core.util;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import se.pj.tbike.service.CrudService;
import se.pj.tbike.util.Output;

public class SimpleCrudService<T, K extends Comparable<K>>
		implements CrudService<T, K> {

	private final JpaRepository<T, K> repository;
	private final Function<T, K> keyProvider;

	public SimpleCrudService(JpaRepository<T, K> repository,
	                         Function<T, K> keyProvider) {
		this.repository = repository;
		this.keyProvider = keyProvider;
	}

	@Override
	public final JpaRepository<T, K> getRepository() {
		return repository;
	}

	@Override
	public Output.Array<T> findAll() {
		return findAll( Sort.unsorted() );
	}

	@Override
	public Output.Array<T> findAll(Sort sort) {
		List<T> list = repository.findAll( sort );
		return Output.array( list );
	}

	@Override
	public Output.Pagination<T> findPage(int num, int size) {
		PageRequest req = PageRequest.of( num, size );
		Page<T> page = repository.findAll( req );
		return Output.pagination(
				page.getContent(),
				page.getNumber(),
				page.getSize(),
				page.getTotalElements(),
				page.getTotalPages()
		);
	}

	@Override
	public Output.Value<T> findByKey(K id) {
		Optional<T> o = repository.findById( id );
		return Output.value( o.orElse( null ) );
	}

	@Override
	public boolean exists(T t) {
		return existsByKey( keyProvider.apply( t ) );
	}

	@Override
	public boolean existsByKey(K id) {
		return repository.existsById( id );
	}

	@Override
	public T create(T t) {
		return repository.save( t );
	}

	@Override
	public boolean update(T newVal, T oldVal) {
		if ( oldVal != null ) {
			if ( !exists( oldVal ) ) {
				return false;
			}
			if ( oldVal.equals( newVal ) ) {
				return true;
			}
		}
		repository.save( newVal );
		return true;
	}

	@Override
	public boolean update(T t) {
		return update( t, null );
	}

	@Override
	public boolean removeByKey(K id) {
		repository.deleteById( id );
		return true;
	}

	@Override
	public boolean remove(T t) {
		repository.delete( t );
		return true;
	}
}

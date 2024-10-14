package se.pj.tbike.util.result.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

import se.pj.tbike.util.result.Result;

public class ResultImpl<T>
		extends ResultListImpl<T>
		implements Result<T> {

	private static final Result<?> EMPTY = new ResultImpl<>( null );

	private final T data;

	public ResultImpl( T data ) {
		super( data != null ? List.of( data ) : List.of() );
		this.data = data;
	}

	@Override
	public Optional<T> toOptional() {
		return Optional.ofNullable( data );
	}

	@Override
	public boolean isEmpty() {
		return toOptional().isEmpty();
	}

	@Override
	public T get() {
		var o = toOptional();
		if ( o.isEmpty() )
			throw new NoSuchElementException( "No value present" );
		return o.get();
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public <R> Result<R> map( Function<T, R> mapper ) {
		if ( mapper == null )
			throw new NullPointerException( "mapper is null" );
		if ( data == null )
			return (Result<R>) EMPTY;
		else
			return new ResultImpl<>( mapper.apply( data ) );
	}
}

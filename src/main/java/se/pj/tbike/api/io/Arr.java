package se.pj.tbike.api.io;

import java.util.Collection;
import java.util.List;

/**
 * Wrapper of array/collection value
 *
 * @param <E> type of element.
 */
public class Arr<E> extends Val<Collection<E>> {

	private Arr( Collection<E> data ) {
		super( data );
		if ( data == null )
			throw new NullPointerException( "data is null" );
	}

	public static <E> Arr<E> of( Collection<E> c ) {
		return new Arr<>( c != null ? c : List.of() );
	}

	@SafeVarargs
	public static <E> Arr<E> of( E... els ) {
		return new Arr<>( List.of( els ) );
	}
}

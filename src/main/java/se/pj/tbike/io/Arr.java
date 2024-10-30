package se.pj.tbike.io;

import se.pj.tbike.util.result.ResultList;

import java.util.Collection;
import java.util.List;

/**
 * Wrapper of array/collection value
 *
 * @param <E> type of element.
 */
public final class Arr<E> extends Val<Collection<E>> {

	private Arr(Collection<E> data) {
		super( data );
		if ( data == null )
			throw new NullPointerException( "data is null" );
	}

	public static <E> Arr<E> of(Collection<E> c) {
		return new Arr<>( c != null ? c : List.of() );
	}

	@Deprecated
	public static <E> Arr<E> of(ResultList<E> rl) {
		return of( rl.toList() );
	}

	@SafeVarargs
	public static <E> Arr<E> of(E... els) {
		return of( List.of( els ) );
	}
}

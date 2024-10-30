package se.pj.tbike.io;

import se.pj.tbike.util.result.Result;
import se.pj.tbike.util.result.ResultList;

import java.util.Collection;

/**
 * Wrapper of single value
 *
 * @param <V> type of value
 */
public sealed class Val<V>
		implements ResponseType
		permits Arr {

	private final V data;

	protected Val(V data) {
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	public static <V> Val<V> wrap(Object o) {
		if ( o instanceof Val<?> val )
			return (Val<V>) val;
		return (Val<V>) new Val<>( o );
	}

	public static <V> Val<V> wrap(Object o, Class<V> cls) {
		Object data = o instanceof Val<?> val ? val.data : o;
		return new Val<>( cls.cast( data ) );
	}

	public static <V> Val<V> of(V v) {
		return new Val<>( v );
	}

	@Deprecated
	public static <V> Val<Collection<V>> of(ResultList<V> r) {
		return Arr.of( r.toList() );
	}

	@Deprecated
	public static <V> Val<V> of(Result<V> r) {
		return new Val<>( r.get() );
	}

	public V get() {
		return data;
	}
}

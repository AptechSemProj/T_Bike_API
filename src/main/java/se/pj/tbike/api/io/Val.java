package se.pj.tbike.api.io;

/**
 * Wrapper of single value
 *
 * @param <V> type of value
 */
public class Val<V> implements ResponseType {

	private final V data;

	protected Val( V data ) {
		this.data = data;
	}

	public static <V> Val<V> of( V v ) {
		return new Val<>( v );
	}

	public V get() {
		return data;
	}
}

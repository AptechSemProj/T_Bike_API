package se.pj.tbike.util.cache;

/**
 * Value wrapper class
 *
 * @param <V> type of value
 */
// package-private
class Placeholder<V> extends ValueHolder<V> {
	Placeholder() {
		super( null );
	}

	boolean isNew( V v ) {
		return v != null;
	}

	@Override
	ValueHolder<V> replace( V v ) {
		if ( isNew( v ) )
			return new ValueHolder<>( v );
		return this;
	}
}

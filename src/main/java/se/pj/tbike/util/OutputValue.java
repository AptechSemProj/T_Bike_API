package se.pj.tbike.util;

import java.util.function.Function;
import java.util.function.Supplier;

public class OutputValue<V>
		implements Output.Value<V> {

	private final V value;

	public OutputValue( V value ) {
		this.value = value;
	}

	@Override
	public <R> Value<R> map( Function<? super V, ? extends R> mapper ) {
		if ( value != null ) {
			return new OutputValue<>( mapper.apply( value ) );
		}
		return new OutputValue<>( null );
	}

	@Override
	public Value<V> or( Supplier<? extends V> supplier ) {
		if ( value != null )
			return this;
		return new OutputValue<>( supplier.get() );
	}

	@Override
	public Value<V> or( V other ) {
		if ( value != null )
			return this;
		return new OutputValue<>( other );
	}

	@Override
	public V get() {
		return value;
	}
}

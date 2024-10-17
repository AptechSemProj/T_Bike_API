package se.pj.tbike.api.util;

import java.util.function.Predicate;
import java.util.function.Supplier;

import lombok.Getter;

@Getter
public class Validated<V> {

	private final Error error;

	private final V value;

	private Validated( Error err, V value ) {
		this.error = err;
		this.value = value;
	}

	public static <V> Validated<V> failure( Error error ) {
		if ( error == null )
			throw new NullPointerException( "error is null" );
		return new Validated<>( error, null );
	}

	public static <V> Validated<V> success( V value ) {
		return new Validated<>( null, value );
	}

	/**
	 * @param other The instance of {@code Validated}.
	 * @param <V>   type of value.
	 * @return The instance of passed {@code Validated} if {@code other.isOk()},
	 * else throw {@code IllegalArgumentException}
	 */
	public static <V> Validated<V> success( Validated<V> other ) {
		if ( other.isFailed() )
			throw new IllegalArgumentException();
		return other;
	}

	/**
	 * @param tester test case
	 * @param error  if test result is false, error will be call.
	 * @return result of test case
	 */
	public Validated<V> thenTest( Predicate<V> tester, Supplier<Error> error ) {
		if ( tester == null )
			throw new NullPointerException( "tester is null" );
		if ( error == null )
			throw new NullPointerException( "error's supplier is null" );
		return isFailed() || tester.test( value )
				? this
				: new Validated<>( error.get(), value );
	}

	public Validated<V> thenTest( Predicate<V> tester, Error error ) {
		return thenTest( tester, () -> error );
	}

	public boolean isFailed() {
		return error != null;
	}

	public boolean isOk() {
		return error == null;
	}
}

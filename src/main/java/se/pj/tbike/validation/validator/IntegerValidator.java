package se.pj.tbike.validation.validator;

import se.pj.tbike.validation.Errors;
import se.pj.tbike.validation.error.MaximumOverflowError;
import se.pj.tbike.validation.error.MinimumOverflowError;

import java.util.function.Consumer;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class IntegerValidator
		extends NumberValidator<Integer> {

	private static final BigInteger MAX =
			BigInteger.valueOf( Integer.MAX_VALUE );
	private static final BigInteger MIN =
			BigInteger.valueOf( Integer.MIN_VALUE );

	public IntegerValidator() {
		super( Integer.class, 0 );
	}

	@Override
	protected Integer convertValue(Number n,
	                               Consumer<MaximumOverflowError> maxErr,
	                               Consumer<MinimumOverflowError> minErr)
			throws IllegalArgumentException {
		if ( n instanceof Integer || n instanceof Short || n instanceof Byte ) {
			return n.intValue();
		}
		BigInteger bigInt;
		if ( n instanceof BigInteger bi ) {
			bigInt = bi;
		} else if ( n instanceof BigDecimal bd ) {
			bigInt = bd.toBigInteger();
		} else if ( n instanceof Long l ) {
			bigInt = BigInteger.valueOf( l );
		} else if ( n instanceof Double d ) {
			bigInt = BigInteger.valueOf( d.longValue() );
		} else {
			bigInt = null;
		}
		if ( bigInt != null ) {
			String reason = "The value " + n;
			if ( bigInt.compareTo( MIN ) < 0 ) {
				reason += " is less than the minimum value of int.";
				minErr.accept(
						Errors.getBuilder( MinimumOverflowError.class )
								.reason( reason )
								.build()
				);
			} else if ( bigInt.compareTo( MAX ) > 0 ) {
			}
			throw new IllegalArgumentException( "Could not convert '" + n +
					"' to " + Integer.class.getName() );
		}
		return n.intValue();
	}

	@Override
	protected Integer parse(String s)
			throws NumberFormatException {
		if ( s == null ) {
			return null;
		}
		if ( isHexNumber( s ) ) {
			return Integer.decode( s );
		} else {
			return Integer.valueOf( s );
		}
	}
}

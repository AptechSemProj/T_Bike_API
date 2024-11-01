package se.pj.tbike.validation.validator;

import se.pj.tbike.validation.error.MaximumOverflowError;
import se.pj.tbike.validation.error.MinimumOverflowError;

import java.util.function.Consumer;

import java.math.BigInteger;

public final class LongValidator
		extends NumberValidator<Long> {

	private static final BigInteger MAX =
			BigInteger.valueOf( Integer.MAX_VALUE );
	private static final BigInteger MIN =
			BigInteger.valueOf( Integer.MIN_VALUE );

	public LongValidator() {
		super( Long.class, 0L );
	}

	// todo
	@Override
	protected Long convertValue(Number n,
	                            Consumer<MaximumOverflowError> maxErr,
	                            Consumer<MinimumOverflowError> minErr)
			throws IllegalArgumentException {
		return n.longValue();
	}

	// todo
	@Override
	protected Long parse(String s) throws NumberFormatException {
		return Long.decode( s );
	}
}

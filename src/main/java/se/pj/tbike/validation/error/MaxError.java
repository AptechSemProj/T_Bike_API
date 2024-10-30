package se.pj.tbike.validation.error;

import se.pj.tbike.validation.Error;

/**
 * The value is greater than max.
 */
public class MaxError extends Error {

	public static final int CODE = 10011;
	public static final String REASON =
			"The value passed is greater than the maximum value.";

	private MaxError(int code, String reason, String guide) {
		super( code, reason, guide );
	}

	public static Builder<MaxError> builder() {
		return new Builder<>() {
			@Override
			public MaxError build() {
				return new MaxError(
						getCode( CODE ),
						getReason( REASON ),
						getGuide()
				);
			}
		};
	}
}

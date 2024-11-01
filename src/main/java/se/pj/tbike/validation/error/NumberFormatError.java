package se.pj.tbike.validation.error;

/**
 * The value is not a number.
 */
public class NumberFormatError extends Error {

	public static final int CODE = 10100;
	public static final String REASON =
			"The value passed is not a number.";

	private NumberFormatError(int code, String reason, String guide) {
		super( code, reason, guide );
	}

	public static Builder<NumberFormatError> builder() {
		return new Builder<>( CODE, REASON ) {
			@Override
			protected NumberFormatError newInstance(int code, String reason,
			                                        String guide) {
				return new NumberFormatError( code, reason, guide );
			}
		};
	}
}

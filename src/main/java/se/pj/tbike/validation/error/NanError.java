package se.pj.tbike.validation.error;

import se.pj.tbike.validation.Error;

/**
 * The value is not a number.
 */
public class NanError extends Error {

	public static final int CODE = 10010;
	public static final String REASON =
			"The value passed is not a number.";

	private NanError(int code, String reason, String guide) {
		super( code, reason, guide );
	}

	public static Builder<NanError> builder() {
		return new Builder<>() {
			@Override
			public NanError build() {
				return new NanError(
						getCode( CODE ),
						getReason( REASON ),
						getGuide()
				);
			}
		};
	}
}

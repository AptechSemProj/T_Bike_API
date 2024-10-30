package se.pj.tbike.validation.error;

import se.pj.tbike.validation.Error;

public class NullError extends Error {

	public static final int CODE = 10000;
	public static final String REASON =
			"The value passed in is null.";

	private NullError(int code, String reason, String guide) {
		super( code, reason, guide );
	}

	public static Builder<NullError> builder() {
		return new Builder<>() {
			@Override
			public NullError build() {
				return new NullError(
						getCode( CODE ),
						getReason( REASON ),
						getGuide()
				);
			}
		};
	}
}

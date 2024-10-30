package se.pj.tbike.validation.error;

import se.pj.tbike.validation.Error;

public class EmptyError extends Error {

	public static final int CODE = 10001;
	public static final String REASON =
			"The value passed in is empty.";

	protected EmptyError(int code, String reason, String guide) {
		super( code, reason, guide );
	}

	public static Builder<EmptyError> builder() {
		return new Builder<>() {
			@Override
			public EmptyError build() {
				return new EmptyError(
						getCode( CODE ),
						getReason( REASON ),
						getGuide()
				);
			}
		};
	}
}

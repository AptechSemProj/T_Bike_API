package se.pj.tbike.validation.error;

import se.pj.tbike.validation.Error;

public class UnknownError extends Error {

	public static final int CODE = 10000;
	public static final String REASON = "Unknown error.";

	private UnknownError(int code, String reason, String guide) {
		super( code, reason, guide );
	}

	public static Builder<UnknownError> builder() {
		return new Builder<>( CODE, REASON ) {
			@Override
			protected UnknownError newInstance(int code, String reason,
			                                   String guide) {
				return new UnknownError( code, reason, guide );
			}
		};
	}
}

package se.pj.tbike.validation.error;

import se.pj.tbike.validation.Error;

public class AlreadyExistsError extends Error {

	public static final int CODE = 14039;
	public static final String REASON = "The passed value already exists.";

	private AlreadyExistsError(int code, String reason, String guide) {
		super( code, reason, guide );
	}

	public static Builder<AlreadyExistsError> builder() {
		return new Builder<>( CODE, REASON ) {
			@Override
			protected AlreadyExistsError newInstance(int code, String reason,
			                                         String guide) {
				return new AlreadyExistsError( code, reason, guide );
			}
		};
	}
}

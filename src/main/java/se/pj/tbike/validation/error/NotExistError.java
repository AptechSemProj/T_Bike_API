package se.pj.tbike.validation.error;

import se.pj.tbike.validation.Error;

public class NotExistError extends Error {

	public static final int CODE = 1007;
	public static final String REASON =
			"The value passed does not exist.";

	private NotExistError( int code, String reason, String guide ) {
		super( code, reason, guide );
	}

	public static Builder<NotExistError> builder() {
		return new Builder<>() {
			@Override
			public NotExistError build() {
				return new NotExistError(
						getCode( CODE ),
						getReason( REASON ),
						getGuide()
				);
			}
		};
	}
}

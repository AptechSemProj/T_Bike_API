package se.pj.tbike.validation.error;

public class NotExistError extends Error {

	public static final int CODE = 14040;
	public static final String REASON = "The value passed does not exist.";

	private NotExistError(int code, String reason, String guide) {
		super( code, reason, guide );
	}

	public static Builder<NotExistError> builder() {
		return new Builder<>( CODE, REASON ) {
			@Override
			protected NotExistError newInstance(int code, String reason,
			                                    String guide) {
				return new NotExistError( code, reason, guide );
			}
		};
	}
}

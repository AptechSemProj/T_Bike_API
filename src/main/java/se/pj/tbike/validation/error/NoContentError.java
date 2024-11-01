package se.pj.tbike.validation.error;

public class NoContentError extends Error {

	public static final int CODE = 12000;
	public static final String REASON =
			"The value passed in is empty or has no content.";

	private NoContentError(int code, String reason, String guide) {
		super( code, reason, guide );
	}

	public static Builder<NoContentError> builder() {
		return new Builder<>( CODE, REASON ) {
			@Override
			protected NoContentError newInstance(int code, String reason,
			                                     String guide) {
				return new NoContentError( code, reason, guide );
			}
		};
	}
}

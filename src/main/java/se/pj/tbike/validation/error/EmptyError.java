package se.pj.tbike.validation.error;

@Deprecated
public class EmptyError extends Error {

	public static final int CODE = -12000;
	public static final String REASON = "The value passed in is empty.";

	private EmptyError(int code, String reason, String guide) {
		super( code, reason, guide );
	}

	public static Builder<EmptyError> builder() {
		return new Builder<>( CODE, REASON ) {
			@Override
			protected EmptyError newInstance(int code, String reason,
			                                 String guide) {
				return new EmptyError( code, reason, guide );
			}
		};
	}
}

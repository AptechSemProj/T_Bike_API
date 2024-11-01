package se.pj.tbike.validation.error;

public class NullValueError extends Error {

	public static final int CODE = 10001;
	public static final String REASON = "The value passed in is null.";

	private NullValueError(int code, String reason, String guide) {
		super( code, reason, guide );
	}

	public static Builder<NullValueError> builder() {
		return new Builder<>( CODE, REASON ) {
			@Override
			protected NullValueError newInstance(int code, String reason,
			                                     String guide) {
				return new NullValueError( code, reason, guide );
			}
		};
	}
}

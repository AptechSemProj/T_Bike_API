package se.pj.tbike.validation.error;

public class PositiveNumberError extends NumberError {

	public static final int CODE = 10104;
	public static final String REASON = "The value passed in is a positive number.";

	private PositiveNumberError(int code, String reason, String guide) {
		super( code, reason, guide );
	}

	public static Builder<PositiveNumberError> builder() {
		return new Builder<>( CODE, REASON ) {
			@Override
			protected PositiveNumberError newInstance(int code, String reason, String guide) {
				return new PositiveNumberError( code, reason, guide );
			}
		};
	}
}

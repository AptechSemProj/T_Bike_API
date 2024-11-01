package se.pj.tbike.validation.error;

public class UnexpectedValueError extends Error {

	public static final int CODE = 10051;
	public static final String REASON =
			"The value passed in is an unexpected value.";

	private UnexpectedValueError(int code, String reason, String guide) {
		super( code, reason, guide );
	}

	public static Builder<UnexpectedValueError> builder() {
		return new Builder<>( CODE, REASON ) {
			@Override
			protected UnexpectedValueError newInstance(int code,
			                                           String reason,
			                                           String guide) {
				return new UnexpectedValueError( code, reason, guide );
			}
		};
	}
}

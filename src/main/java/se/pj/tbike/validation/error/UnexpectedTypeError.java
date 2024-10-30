package se.pj.tbike.validation.error;

public class UnexpectedTypeError
		extends UnexpectedError {

	public static final int CODE = 10051;
	public static final String REASON = "Unexpected data type.";

	protected UnexpectedTypeError(int code, String reason, String guide) {
		super( code, reason, guide );
	}

	public static Builder<UnexpectedError> builder() {
		return new Builder<>() {
			@Override
			public UnexpectedTypeError build() {
				return new UnexpectedTypeError(
						getCode( CODE ),
						getReason( REASON ),
						getGuide()
				);
			}
		};
	}
}

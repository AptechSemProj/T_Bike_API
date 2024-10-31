package se.pj.tbike.validation.error;

public class UnexpectedError extends Error {

	public static final int CODE = 1005;
	public static final String REASON =
			"The value passed in is an unexpected value.";

	protected UnexpectedError(int code, String reason, String guide) {
		super( code, reason, guide );
	}

	public static Builder<UnexpectedError> builder() {
		return new Builder<>() {
			@Override
			public UnexpectedError build() {
				return new UnexpectedError(
						getCode( CODE ),
						getReason( REASON ),
						getGuide()
				);
			}
		};
	}
}

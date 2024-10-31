package se.pj.tbike.validation.error;

public class UnknownError extends Error {

	public static final int CODE = 1999;
	public static final String REASON = "Unknown error.";

	private UnknownError(int code, String reason, String guide) {
		super( code, reason, guide );
	}

	public static Builder<UnknownError> builder() {
		return new Builder<>() {
			@Override
			public UnknownError build() {
				return new UnknownError(
						getCode( CODE ),
						getReason( REASON ),
						getGuide()
				);
			}
		};
	}
}

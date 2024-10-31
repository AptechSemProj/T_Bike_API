package se.pj.tbike.validation.error;

@Deprecated
public class InvalidError extends Error {

	public static final int CODE = 1004;
	public static final String REASON =
			"The value passed is an invalid value.";

	private InvalidError(int code, String reason, String guide) {
		super( code, reason, guide );
	}

	public static Builder<InvalidError> builder() {
		return new Builder<>() {
			@Override
			public InvalidError build() {
				return new InvalidError(
						getCode( CODE ),
						getReason( REASON ),
						getGuide()
				);
			}
		};
	}
}

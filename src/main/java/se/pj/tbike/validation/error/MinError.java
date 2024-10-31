package se.pj.tbike.validation.error;

/**
 * Check value is less than min.
 */
public class MinError extends Error {

	public static final int CODE = 10012;
	public static final String REASON =
			"The value passed in is less than the minimum value.";

	private MinError(int code, String reason, String guide) {
		super( code, reason, guide );
	}

	public static Builder<MinError> builder() {
		return new Builder<>() {
			@Override
			public MinError build() {
				return new MinError(
						getCode( CODE ),
						getReason( REASON ),
						getGuide()
				);
			}
		};
	}
}

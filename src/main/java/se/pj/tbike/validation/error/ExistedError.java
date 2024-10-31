package se.pj.tbike.validation.error;

public class ExistedError extends Error {

	public static final int CODE = 1006;
	public static final String REASON =
			"The passed value already exists.";

	private ExistedError(int code, String reason, String guide) {
		super( code, reason, guide );
	}

	public static Builder<ExistedError> builder() {
		return new Builder<>() {
			@Override
			public ExistedError build() {
				return new ExistedError(
						getCode( CODE ),
						getReason( REASON ),
						getGuide()
				);
			}
		};
	}
}

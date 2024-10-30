package se.pj.tbike.validation.validator;

public class LongValidator extends NumberValidator<Long> {

	public LongValidator() {
		super();
	}

	@Override
	protected Class<Long> getNumberType() {
		return Long.class;
	}
}

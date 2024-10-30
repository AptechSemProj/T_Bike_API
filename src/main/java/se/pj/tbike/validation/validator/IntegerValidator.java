package se.pj.tbike.validation.validator;

public class IntegerValidator extends NumberValidator<Integer> {

	public IntegerValidator() {
		super();
	}

	@Override
	protected Class<Integer> getNumberType() {
		return Integer.class;
	}
}

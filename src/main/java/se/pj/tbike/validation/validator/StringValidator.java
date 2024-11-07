package se.pj.tbike.validation.validator;

import se.pj.tbike.validation.Requirement;
import se.pj.tbike.validation.ValidationResult;
import se.pj.tbike.validation.Validator;
import se.pj.tbike.validation.Error;

import java.util.Map;
import java.util.Set;

public class StringValidator extends Validator {


	public StringValidator acceptNull(boolean accept) {
		return this;
	}

	public StringValidator acceptEmpty(boolean accept) {
		return this;
	}

	public StringValidator acceptBlank(boolean accept) {
		return this;
	}

	public StringValidator acceptLength(int length) {
		return this;
	}

	public StringValidator acceptMaxLength(int length) {
		return this;
	}

	public StringValidator acceptMinLength(int length) {
		return this;
	}

	@Override
	public int accept(Requirement... requirements) {
		return 0;
	}

	@Override
	public Set<Requirement> getRequirements() {
		return Set.of();
	}

	@Override
	public ValidationResult validate(Object value) {
		return null;
	}

	@Override
	public Set<Error> getReturnableErrors() {
		return Set.of();
	}

}

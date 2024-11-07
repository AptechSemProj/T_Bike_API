package se.pj.tbike.validation.validator;

import se.pj.tbike.validation.Requirement;
import se.pj.tbike.validation.ValidationResult;
import se.pj.tbike.validation.Validator;
import se.pj.tbike.validation.Error;

import java.util.Set;

public class ArrayValidator extends Validator {
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

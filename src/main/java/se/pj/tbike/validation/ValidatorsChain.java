package se.pj.tbike.validation;

import se.pj.tbike.validation.validator.Validator;

import java.util.LinkedHashSet;
import java.util.Set;

@Deprecated
public final class ValidatorsChain {

	private final Set<Validator> validators;

	private ValidatorsChain() {
		this.validators = new LinkedHashSet<>();
	}

	public static ValidatorsChain createChain() {
		return new ValidatorsChain();
	}

	public ValidatorsChain merge(ValidatorsChain other,
	                             MergeType type) {
		for ( Validator v : other.validators ) {
			addValidator( v, type );
		}
		return this;
	}

	public ValidatorsChain merge(ValidatorsChain other) {
		return merge( other, null );
	}

	public <T> ValidatorsChain addValidator(Validator validator,
	                                        MergeType type) {
		boolean replaceExisting = (type == MergeType.REPLACE_IF_EXISTS);
		if ( !this.validators.contains( validator ) || replaceExisting ) {
			this.validators.add( validator );
		}
		return this;
	}

	public <T> ValidatorsChain addValidator(Validator validator) {
		return addValidator( validator, MergeType.REPLACE_IF_EXISTS );
	}

	public <V> ValidationResult handle(Object value) {
		Object val = value;
		ValidationResult result = ValidationResult.success( val );
		for ( Validator validator : this.validators ) {
			result = validator.validate( val );
			if ( result.isFailed() ) {
				break;
			} else {
				val = result.getValue();
			}
		}
		return result;
	}

	public boolean isEmpty() {
		return validators.isEmpty();
	}

	public enum MergeType {
		REPLACE_IF_EXISTS,
	}
}

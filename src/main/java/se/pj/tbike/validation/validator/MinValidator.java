package se.pj.tbike.validation.validator;

import se.pj.tbike.validation.Errors;
import se.pj.tbike.validation.ValidationResult;
import se.pj.tbike.validation.Validator;
import se.pj.tbike.validation.error.MinError;

import java.util.Objects;

@Deprecated
public abstract class MinValidator<N extends Number & Comparable<N>>
		implements Validator {

	private final MinError minError = Errors.get( MinError.class );

	private final NumberValidator<N> numberValidator;
	private final Class<N> numberType;

	public MinValidator(NumberValidator<N> numberValidator) {
		if ( numberValidator == null ) {
			throw new IllegalArgumentException();
		}
		this.numberValidator = numberValidator;
		this.numberType = numberValidator.getNumberType();
	}

	protected abstract boolean isLessThanMin(N value);

	@Override
	public void applyConf(Configuration conf) {

	}

	@Override
	public final ValidationResult validate(Object value) {
		ValidationResult result = numberValidator.validate( value );
		if ( result.isOk() ) {
			N num = result.getValue( numberType );
			if ( isLessThanMin( num ) ) {
				return ValidationResult.failure( minError, num );
			} else {
				return ValidationResult.success( num );
			}
		}
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof MinValidator<?> that) ) {
			return false;
		}
		return Objects.equals( numberValidator, that.numberValidator ) &&
				Objects.equals( numberType, that.numberType ) &&
				Objects.equals( minError, that.minError );
	}

	@Override
	public int hashCode() {
		return Objects.hash( numberValidator, numberType, minError );
	}
}

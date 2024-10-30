package se.pj.tbike.validation.validator;

import se.pj.tbike.validation.Errors;
import se.pj.tbike.validation.ValidationResult;
import se.pj.tbike.validation.Validator;
import se.pj.tbike.validation.error.MaxError;

import java.util.Objects;

@Deprecated
public abstract class MaxValidator<N extends Number & Comparable<N>>
		implements Validator {

	private final MaxError maxError = Errors.get( MaxError.class );

	private final NumberValidator<N> numberValidator;
	private final Class<N> numberType;

	public MaxValidator(NumberValidator<N> numberValidator) {
		if ( numberValidator == null ) {
			throw new IllegalArgumentException();
		}
		this.numberValidator = numberValidator;
		this.numberType = numberValidator.getNumberType();
	}

	protected abstract boolean isGreaterThanMax(N value);

	@Override
	public void applyConf(Configuration conf) {

	}

	@Override
	public final ValidationResult validate(Object value) {
		ValidationResult result = numberValidator.validate( value );
		if ( result.isOk() ) {
			N num = result.getValue( numberType );
			if ( isGreaterThanMax( num ) ) {
				return ValidationResult.failure( maxError, num );
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
		if ( !(o instanceof MaxValidator<?> that) ) {
			return false;
		}
		return Objects.equals( numberValidator, that.numberValidator ) &&
				Objects.equals( numberType, that.numberType ) &&
				Objects.equals( maxError, that.maxError );
	}

	@Override
	public int hashCode() {
		return Objects.hash( numberValidator, numberType, maxError );
	}
}

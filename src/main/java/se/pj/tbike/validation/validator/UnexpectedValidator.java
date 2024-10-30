package se.pj.tbike.validation.validator;

import se.pj.tbike.validation.Errors;
import se.pj.tbike.validation.ValidationResult;
import se.pj.tbike.validation.Validator;
import se.pj.tbike.validation.error.UnexpectedError;

import java.util.Objects;

public class UnexpectedValidator<T>
		implements Validator {

	private final T expectValue;
	private final UnexpectedError error;

	protected UnexpectedValidator(T expectValue,
	                              UnexpectedError error) {
		if ( error == null ) {
			throw new IllegalArgumentException();
		}
		this.expectValue = expectValue;
		this.error = error;
	}

	public UnexpectedValidator(T expectValue) {
		this( expectValue, Errors.get( UnexpectedError.class ) );
	}

	protected boolean isExpectedValue(T expectValue, Object value) {
		return Objects.equals( expectValue, value );
	}

	@Override
	public final ValidationResult validate(Object value) {
		if ( isExpectedValue( expectValue, value ) ) {
			return ValidationResult.success( expectValue );
		}
		return ValidationResult.failure( error );
	}

	@Override
	public void applyConf(Configuration conf) {

	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof UnexpectedValidator<?> that) ) {
			return false;
		}
		return Objects.equals( expectValue, that.expectValue ) &&
				Objects.equals( error, that.error );
	}

	@Override
	public int hashCode() {
		return Objects.hash( expectValue, error );
	}
}

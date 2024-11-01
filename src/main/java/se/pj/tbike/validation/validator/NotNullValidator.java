package se.pj.tbike.validation.validator;

import se.pj.tbike.validation.Errors;
import se.pj.tbike.validation.ValidationResult;
import se.pj.tbike.validation.error.NullValueError;

import java.util.Objects;

public class NotNullValidator
		implements Validator {

	private final NullValueError nullValueError = Errors.get( NullValueError.class );

	@Override
	public final ValidationResult validate(Object value) {
		if ( value == null ) {
			return ValidationResult.failure( nullValueError );
		}
		return ValidationResult.success( value );
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof NotNullValidator that) ) {
			return false;
		}
		return Objects.equals( nullValueError, that.nullValueError );
	}

	@Override
	public int hashCode() {
		return Objects.hashCode( nullValueError );
	}
}

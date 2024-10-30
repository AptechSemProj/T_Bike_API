package se.pj.tbike.validation.validator;

import se.pj.tbike.validation.Errors;
import se.pj.tbike.validation.ValidationResult;
import se.pj.tbike.validation.Validator;
import se.pj.tbike.validation.error.NullError;

import java.util.Objects;

public class NotNullValidator
		implements Validator {

	private final NullError nullError = Errors.get( NullError.class );

	@Override
	public final ValidationResult validate(Object value) {
		if ( value == null ) {
			return ValidationResult.failure( nullError );
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
		return Objects.equals( nullError, that.nullError );
	}

	@Override
	public int hashCode() {
		return Objects.hashCode( nullError );
	}
}

package se.pj.tbike.validation.validator;

import se.pj.tbike.validation.Errors;
import se.pj.tbike.validation.ValidationResult;
import se.pj.tbike.validation.error.UnexpectedValueError;

import java.util.Objects;

public class UnexpectedValidator<T>
		implements Validator {

	private final T expectValue;
	private final UnexpectedValueError error;

	protected UnexpectedValidator(T expectValue,
	                              UnexpectedValueError error) {
		if ( error == null ) {
			throw new IllegalArgumentException();
		}
		this.expectValue = expectValue;
		this.error = error;
	}

	public UnexpectedValidator(T expectValue) {
		this( expectValue, Errors.get( UnexpectedValueError.class ) );
	}

	private boolean isExpectedType(Object value, Class<?> type) {
		if ( value instanceof Class<?> cls ) {
			if ( cls == byte.class ) {
				return type == Byte.class;
			} else if ( cls == short.class ) {
				return type == Short.class;
			} else if ( cls == int.class ) {
				return type == Integer.class;
			} else if ( cls == long.class ) {
				return type == Long.class;
			} else if ( cls == float.class ) {
				return type == Float.class;
			} else if ( cls == double.class ) {
				return type == Double.class;
			} else if ( cls == char.class ) {
				return type == Character.class;
			} else if ( cls == boolean.class ) {
				return type == Boolean.class;
			} else {
				return type.isAssignableFrom( cls );
			}
		}
		return type.isInstance( value );
	}

	private boolean isExpectedValue(T expectValue, Object value) {
		return Objects.equals( expectValue, value );
	}

	@Override
	public final ValidationResult validate(Object value) {
		if ( isExpectedType( value, expectValue.getClass() ) ) {

		}

		if ( isExpectedValue( expectValue, value ) ) {
			return ValidationResult.success( expectValue );
		}
		return ValidationResult.failure( error );
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

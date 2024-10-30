package se.pj.tbike.validation.validator;

import se.pj.tbike.reflect.GenericType;
import se.pj.tbike.validation.ValidationResult;
import se.pj.tbike.validation.Validator;
import se.pj.tbike.validation.error.InvalidError;

import java.util.Objects;

@Deprecated
public abstract class InvalidValidator<T>
		implements Validator {

	private static final InvalidError ERROR =
			InvalidError.builder().build();

	private final InvalidError error;
	private final Class<T> type;

	public InvalidValidator(InvalidError error) {
		if ( error == null ) {
			throw new IllegalArgumentException();
		}
		this.error = error;
		var genericType = new GenericType<>( this );
		this.type = genericType.getType( 0 );
	}

	public InvalidValidator() {
		this( ERROR );
	}

	protected abstract boolean isValid(T value);

	@Override
	public void applyConf(Configuration conf) {

	}

	@Override
	public final ValidationResult validate(Object value) {
		@SuppressWarnings("unchecked") T val = (T) value;
//		if ( value != null ) {
//			UnexpectedTypeValidator utv = new UnexpectedTypeValidator( type );
//			ValidationResult rs = utv.validate( value.getClass() );
//			if ( rs.isFailed() ) {
//				return rs;
//			} else {
//				val = rs.getValue( type );
//			}
//		} else {
//			val = null;
//		}
		if ( isValid( val ) ) {
			return ValidationResult.success( val );
		}
		return ValidationResult.failure( error, val );
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof InvalidValidator<?> that) ) {
			return false;
		}
		return Objects.equals( error, that.error );
	}

	@Override
	public int hashCode() {
		return Objects.hash( error );
	}
}

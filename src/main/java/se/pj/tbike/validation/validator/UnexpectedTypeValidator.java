package se.pj.tbike.validation.validator;

import se.pj.tbike.validation.Errors;
import se.pj.tbike.validation.error.UnexpectedTypeError;

public class UnexpectedTypeValidator
		extends UnexpectedValidator<Class<?>> {

	public UnexpectedTypeValidator(Class<?> expectType) {
		super( expectType, Errors.get( UnexpectedTypeError.class ) );
	}

	@Override
	protected boolean isExpectedValue(Class<?> type, Object value) {
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
}

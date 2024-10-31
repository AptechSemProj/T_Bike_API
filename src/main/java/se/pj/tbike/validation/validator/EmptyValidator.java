package se.pj.tbike.validation.validator;

import se.pj.tbike.validation.Errors;
import se.pj.tbike.validation.ValidationResult;
import se.pj.tbike.validation.error.EmptyError;
import se.pj.tbike.validation.error.UnknownError;

import java.util.Collection;

public class EmptyValidator
		implements Validator {

	private final EmptyError emptyError =
			Errors.get( EmptyError.class );
	private final UnknownError unknownError =
			Errors.get( UnknownError.class );

	@Override
	public ValidationResult validate(Object value) {
		if ( value instanceof String s ) {
			if ( !s.isEmpty() ) {
				return ValidationResult.success( s );
			}
		} else if ( value instanceof Collection<?> c ) {
			if ( !c.isEmpty() ) {
				return ValidationResult.success( c );
			}
		} else if ( value instanceof Object[] os ) {
			if ( os.length > 0 ) {
				return ValidationResult.success( os );
			}
		} else if ( value instanceof byte[] bs ) {
			if ( bs.length > 0 ) {
				return ValidationResult.success( bs );
			}
		} else if ( value instanceof short[] ss ) {
			if ( ss.length > 0 ) {
				return ValidationResult.success( ss );
			}
		} else if ( value instanceof int[] is ) {
			if ( is.length > 0 ) {
				return ValidationResult.success( is );
			}
		} else if ( value instanceof long[] ls ) {
			if ( ls.length > 0 ) {
				return ValidationResult.success( ls );
			}
		} else if ( value instanceof float[] fs ) {
			if ( fs.length > 0 ) {
				return ValidationResult.success( fs );
			}
		} else if ( value instanceof double[] ds ) {
			if ( ds.length > 0 ) {
				return ValidationResult.success( ds );
			}
		} else if ( value instanceof char[] cs ) {
			if ( cs.length > 0 ) {
				return ValidationResult.success( cs );
			}
		} else if ( value instanceof boolean[] bs ) {
			if ( bs.length > 0 ) {
				return ValidationResult.success( bs );
			}
		} else {
			return ValidationResult.failure( unknownError );
		}
		return ValidationResult.failure( emptyError );
	}

	@Override
	public void applyConf(Configuration conf) {

	}
}

package se.pj.tbike.api.util;

import java.util.function.Function;

import static se.pj.tbike.api.util.Error.NaN;
import static se.pj.tbike.api.util.Error.NULL;
import static se.pj.tbike.api.util.Validated.failure;
import static se.pj.tbike.api.util.Validated.success;

public class NumberValidator {

	public static Validated<Integer> validateInt( String s ) {
		return validate( s, Integer::parseInt );
	}

	public static Validated<Long> validateLong( String s ) {
		return validate( s, Long::parseLong );
	}

	public static <T extends Number>
	Validated<T> validate( String s, Function<String, T> parser ) {
		if ( s == null ) return failure( NULL );
		try {
			return success( parser.apply( s ) );
		} catch ( NumberFormatException e ) {
			return failure( NaN );
		}
	}
}

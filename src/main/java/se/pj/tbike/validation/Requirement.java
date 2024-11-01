package se.pj.tbike.validation;

import se.pj.tbike.validation.validator.*;

import java.util.function.Function;

public final class Requirement {

	private final Function<Object, ValidationResult> validator;

	private Requirement(Function<Object, ValidationResult> validator) {
		this.validator = validator;
	}

	public static Requirement notNull() {
		return new Requirement( new NotNullValidator()::validate );
	}

	public static Requirement notEmpty() {
		return new Requirement( (o) -> {
			Requirement nonNull = notNull();
			ValidationResult rs = nonNull.validate( o );
			if ( rs.isFailed() ) {
				return rs;
			}
			EmptyValidator empty = new EmptyValidator();
			return empty.validate( rs.getValue() );
		} );
	}

	public static Requirement notBlank() {
		return new Requirement( (o) -> {
			var rs = notEmpty().validate( o );
			if ( rs.isFailed() ) {
				return rs;
			}
			return null;
//				new InvalidValidator<String>() {
//					@Override
//					protected boolean isValid(String value) {
//						return value != null && !value.isBlank();
//					}
//				}
		} );
	}
//
//	public static Requirement notBlank(int max) {
//		return new Requirement(
//				new InvalidValidator<String>() {
//					@Override
//					protected boolean isValid(String value) {
//						return value != null && !value.isBlank();
//					}
//				}
//		);
//	}

	public static Requirement min(int minValue) {
		var validator = new IntegerValidator()
				.acceptMinValue( minValue );
		return new Requirement( validator::validate );
	}

	public static Requirement min(long minValue) {
		var validator = new LongValidator()
				.acceptMinValue( minValue );
		return new Requirement( validator::validate );
	}

	//package-private
	ValidationResult validate(Object v) {
		return validator.apply( v );
	}
}

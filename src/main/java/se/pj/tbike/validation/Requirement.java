package se.pj.tbike.validation;

import se.pj.tbike.validation.validator.EmptyValidator;
import se.pj.tbike.validation.validator.IntegerValidator;
import se.pj.tbike.validation.validator.LongValidator;
import se.pj.tbike.validation.validator.MinValidator;
import se.pj.tbike.validation.validator.NotNullValidator;

import java.util.function.Function;

public final class Requirement {

	private final Function<Object, ValidationResult> validator;

	private Requirement(Function<Object, ValidationResult> validator) {
		this.validator = validator;
	}

	public static Requirement nonNull() {
		return new Requirement( new NotNullValidator()::validate );
	}

	public static Requirement min(int minValue) {
		var minValidator = new MinValidator<>( new IntegerValidator() ) {
			@Override
			protected boolean isLessThanMin(Integer current) {
				return current < minValue;
			}
		};
		return new Requirement( minValidator::validate );
	}

	public static Requirement min(long minValue) {
		var minValidator = new MinValidator<>( new LongValidator() ) {
			@Override
			protected boolean isLessThanMin(Long current) {
				return current < minValue;
			}
		};
		return new Requirement( minValidator::validate );
	}

	public static Requirement notEmpty() {
		return new Requirement( (o) -> {
			Requirement nonNull = nonNull();
			ValidationResult rs = nonNull.test( o );
			if ( rs.isFailed() ) {
				return rs;
			}
			EmptyValidator empty = new EmptyValidator();
			return empty.validate( rs.getValue() );
		} );
	}
//
//	public static Requirement notBlank() {
//		return new Requirement( (o) -> {
//			var rs = Validators.NULL.validate( o );
//			if ( rs.isFailed() ) {
//				return rs;
//			}
////				new InvalidValidator<String>() {
////					@Override
////					protected boolean isValid(String value) {
////						return value != null && !value.isBlank();
////					}
////				}
//		} );
//	}
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

	//package-private
	ValidationResult test(Object v) {
		return validator.apply( v );
	}
}

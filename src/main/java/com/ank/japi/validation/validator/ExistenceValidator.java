package com.ank.japi.validation.validator;

import com.ank.japi.validation.Errors;
import com.ank.japi.validation.ValidationError;
import com.ank.japi.validation.Validator;
import com.ank.japi.validation.error.AlreadyExistsError;
import com.ank.japi.validation.error.NotExistError;
import com.ank.japi.validation.error.UnexpectedTypeError;
import com.ank.japi.validation.error.UnknownError;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

@Deprecated
public final class ExistenceValidator<T>
        extends Validator {

    public static final int ALREADY_EXISTS = 0;
    public static final int NOT_EXIST      = 1;

    private static final ValidationError[] ERRORS = new ValidationError[] {
            Errors.get( UnexpectedTypeError.class ),
            Errors.get( UnknownError.class ),
            Errors.get( AlreadyExistsError.class ),
            Errors.get( NotExistError.class )
    };

    private Predicate<T> tester;
    private int          check = ALREADY_EXISTS;

    public ExistenceValidator(Collection<T> collection) {
        if ( collection == null ) {
            throw new IllegalArgumentException();
        }
        this.tester = collection::contains;
    }

    public ExistenceValidator() {
        this( Collections.emptySet() );
    }

    public ExistenceValidator<T> acceptAlreadyExists() {
        check = ALREADY_EXISTS;
        return this;
    }

    public ExistenceValidator<T> acceptNotExist() {
        check = NOT_EXIST;
        return this;
    }

    public ExistenceValidator<T> setTester(Predicate<T> func) {
        this.tester = func;
        return this;
    }

//	@Override
//	public ValidationResult validate(Object value) {
//		try {
//			@SuppressWarnings("unchecked")
//			T val = (T) value;
//			switch ( check ) {
//				case ALREADY_EXISTS -> {
//					if ( tester.test( val ) ) {
//						return ValidationResult.success( val );
//					} else {
//						return ValidationResult.failure(
//								Errors.get( NotExistError.class ),
//								val
//						);
//					}
//				}
//				case NOT_EXIST -> {
//					if ( tester.test( val ) ) {
//						return ValidationResult.failure(
//								Errors.get( AlreadyExistsError.class ),
//								val
//						);
//					} else {
//						return ValidationResult.success( val );
//					}
//				}
//				default -> {
//					return ValidationResult.failure(
//							Errors.get( UnknownError.class )
//					);
//				}
//			}
//		} catch ( ClassCastException e ) {
//			return ValidationResult.failure(
//					Errors.get( UnexpectedTypeError.class )
//			);
//		}
//	}

}

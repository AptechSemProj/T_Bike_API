package se.pj.tbike.validation.validator;

import se.pj.tbike.validation.Errors;
import se.pj.tbike.validation.Requirement;
import se.pj.tbike.validation.ValidationResult;
import se.pj.tbike.validation.Validator;
import se.pj.tbike.validation.error.AlreadyExistsError;
import se.pj.tbike.validation.Error;
import se.pj.tbike.validation.error.NotExistError;
import se.pj.tbike.validation.error.UnexpectedTypeError;
import se.pj.tbike.validation.error.UnknownError;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;

public final class ExistenceValidator<T> extends Validator {

	public static final int ALREADY_EXISTS = 0;
	public static final int NOT_EXIST = 1;

	private static final Error[] ERRORS = new Error[] {
			Errors.get( UnexpectedTypeError.class ),
			Errors.get( UnknownError.class ),
			Errors.get( AlreadyExistsError.class ),
			Errors.get( NotExistError.class )
	};

	private Predicate<T> tester;
	private int check = ALREADY_EXISTS;

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

	@Override
	public int accept(Requirement... requirements) {
		return 0;
	}

	@Override
	public Set<Requirement> getRequirements() {
		return Set.of();
	}

	@Override
	public ValidationResult validate(Object value) {
		try {
			@SuppressWarnings("unchecked")
			T val = (T) value;
			switch ( check ) {
				case ALREADY_EXISTS -> {
					if ( tester.test( val ) ) {
						return ValidationResult.success( val );
					} else {
						return ValidationResult.failure(
								Errors.get( NotExistError.class ),
								val
						);
					}
				}
				case NOT_EXIST -> {
					if ( tester.test( val ) ) {
						return ValidationResult.failure(
								Errors.get( AlreadyExistsError.class ),
								val
						);
					} else {
						return ValidationResult.success( val );
					}
				}
				default -> {
					return ValidationResult.failure(
							Errors.get( UnknownError.class )
					);
				}
			}
		} catch ( ClassCastException e ) {
			return ValidationResult.failure(
					Errors.get( UnexpectedTypeError.class )
			);
		}
	}

	@Override
	public Set<Error> getReturnableErrors() {
		return Set.of();
	}

}

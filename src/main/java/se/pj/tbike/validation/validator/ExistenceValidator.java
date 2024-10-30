package se.pj.tbike.validation.validator;

import se.pj.tbike.validation.Errors;
import se.pj.tbike.validation.ValidationResult;
import se.pj.tbike.validation.Validator;
import se.pj.tbike.validation.error.ExistedError;
import se.pj.tbike.validation.error.NotExistError;
import se.pj.tbike.validation.error.UnknownError;

import java.util.Collection;
import java.util.Collections;

public class ExistenceValidator
		implements Validator {

	public static final int EXISTED = 0;
	public static final int NOT_EXIST = 1;

	private final ExistedError existedError =
			Errors.get( ExistedError.class );
	private final NotExistError notExistError =
			Errors.get( NotExistError.class );
	private final UnknownError unknownError =
			Errors.get( UnknownError.class );

	private final Collection<Object> collection;
	private int requirement = EXISTED;

	public ExistenceValidator(Collection<Object> collection) {
		if ( collection == null ) {
			throw new IllegalArgumentException();
		}
		this.collection = collection;
	}

	public ExistenceValidator() {
		this( Collections.emptySet() );
	}

	public ExistenceValidator requireExisted() {
		requirement = EXISTED;
		return this;
	}

	public ExistenceValidator requireNotExist() {
		requirement = NOT_EXIST;
		return this;
	}

	protected boolean isExisted(Object value) {
		return collection.contains( value );
	}

	@Override
	public final ValidationResult validate(Object value) {
		boolean isExisted = isExisted( value );
		switch ( requirement ) {
			case EXISTED -> {
				if ( isExisted ) {
					return ValidationResult.success( value );
				} else {
					return ValidationResult.failure( notExistError, value );
				}
			}
			case NOT_EXIST -> {
				if ( isExisted ) {
					return ValidationResult.failure( existedError, value );
				} else {
					return ValidationResult.success( value );
				}
			}
			default -> {
				return ValidationResult.failure( unknownError );
			}
		}
	}

	@Override
	public void applyConf(Configuration conf) {

	}
}

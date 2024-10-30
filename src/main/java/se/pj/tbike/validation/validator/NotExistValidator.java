package se.pj.tbike.validation.validator;

import se.pj.tbike.validation.ValidationResult;
import se.pj.tbike.validation.Validator;
import se.pj.tbike.validation.error.NotExistError;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Deprecated
public class NotExistValidator
		implements Validator {

	private static final NotExistError ERROR =
			NotExistError.builder().build();

	private final Collection<Object> collection;
	private final NotExistError error;

	public NotExistValidator(Collection<Object> collection,
	                         NotExistError error) {
		if ( collection == null ) {
			throw new IllegalArgumentException();
		}
		if ( error == null ) {
			throw new IllegalArgumentException();
		}
		this.collection = collection;
		this.error = error;
	}

	public NotExistValidator(Collection<Object> collection) {
		this( collection, ERROR );
	}

	public NotExistValidator() {
		this( Collections.emptySet() );
	}

	protected boolean isExisted(Object value) {
		return collection.contains( value );
	}

	@Override
	public ValidationResult validate(Object value) {
		if ( isExisted( value ) ) {
			return ValidationResult.success( value );
		}
		return ValidationResult.failure( error, value );
	}

	@Override
	public void applyConf(Configuration conf) {

	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof NotExistValidator that) ) {
			return false;
		}
		return Objects.equals( collection, that.collection ) &&
				Objects.equals( error, that.error );
	}

	@Override
	public int hashCode() {
		return Objects.hash( collection, error );
	}
}

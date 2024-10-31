package se.pj.tbike.validation.validator;

import se.pj.tbike.validation.ValidationResult;
import se.pj.tbike.validation.error.ExistedError;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Deprecated
public class ExistedValidator implements Validator {

	private static final ExistedError ERROR =
			ExistedError.builder().build();

	private final Collection<Object> collection;
	private final ExistedError error;

	public ExistedValidator(Collection<Object> collection,
	                        ExistedError error) {
		if ( collection == null ) {
			throw new NullPointerException();
		}
		if ( error == null ) {
			throw new NullPointerException();
		}
		this.collection = collection;
		this.error = error;
	}

	public ExistedValidator(Collection<Object> collection) {
		this( collection, ERROR );
	}

	public ExistedValidator() {
		this( Collections.emptySet() );
	}

	protected boolean isExisted(Object value) {
		return collection.contains( value );
	}

	@Override
	public final ValidationResult validate(Object value) {
		if ( isExisted( value ) ) {
			return ValidationResult.failure( error, value );
		}
		return ValidationResult.success( value );
	}

	@Override
	public void applyConf(Configuration conf) {

	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof ExistedValidator that) ) {
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

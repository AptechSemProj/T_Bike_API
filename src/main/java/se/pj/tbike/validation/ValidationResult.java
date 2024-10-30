package se.pj.tbike.validation;

import lombok.Getter;

import java.util.Objects;

@Getter
public final class ValidationResult {

	private final Error error;

	private final Object value;

	private ValidationResult(Error err, Object value) {
		this.error = err;
		this.value = value;
	}

	public static ValidationResult success(Object value) {
		return new ValidationResult( null, value );
	}

	public static ValidationResult failure(Error error, Object value) {
		if ( error == null ) {
			throw new NullPointerException( "error is null" );
		}
		return new ValidationResult( error, value );
	}

	public static ValidationResult failure(Error error) {
		return failure( error, null );
	}

	public <V> V getValue(Class<V> type) {
		return type.cast( this.value );
	}

	public boolean isFailed() {
		return error != null;
	}

	public boolean isOk() {
		return error == null;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof ValidationResult that) ) {
			return false;
		}
		return Objects.equals( error, that.error ) &&
				Objects.equals( value, that.value );
	}

	@Override
	public int hashCode() {
		return Objects.hash( error, value );
	}

	@Override
	public String toString() {
		if ( isOk() ) {
			return "[ Successful ] ~ [ value = " + value + " ]";
		} else {
			return "[ Failed ] ~ [ error = " + error + " ]";
		}
	}
}
package se.pj.tbike.validation;

import se.pj.tbike.validation.error.EmptyError;
import se.pj.tbike.validation.error.ExistedError;
import se.pj.tbike.validation.error.MaxError;
import se.pj.tbike.validation.error.MinError;
import se.pj.tbike.validation.error.NanError;
import se.pj.tbike.validation.error.NotExistError;
import se.pj.tbike.validation.error.NullError;
import se.pj.tbike.validation.error.UnexpectedError;
import se.pj.tbike.validation.error.UnexpectedTypeError;
import se.pj.tbike.validation.error.UnknownError;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class Errors {

	private static final class Instance {
		public static final Errors INSTANCE = new Errors();

		static {
			// configure default errors instance.
			Errors.configure( EmptyError.builder().build() );
			Errors.configure( ExistedError.builder().build() );
			Errors.configure( MaxError.builder().build() );
			Errors.configure( MinError.builder().build() );
			Errors.configure( NotExistError.builder().build() );
			Errors.configure( NullError.builder().build() );
			Errors.configure( NanError.builder().build() );
			Errors.configure( UnexpectedError.builder().build() );
			Errors.configure( UnexpectedTypeError.builder().build() );
			Errors.configure( UnknownError.builder().build() );
		}
	}

	private final Map<Class<? extends Error>, Error> errors;

	private Errors() {
		this.errors = new LinkedHashMap<>();
	}

	public static <E extends Error> void configure(E error) {
		if ( error == null ) {
			throw new IllegalArgumentException();
		}
		Errors manager = Instance.INSTANCE;
		manager.errors.put( error.getClass(), error );
	}

	public static <E extends Error> E get(Class<E> type) {
		Errors manager = Instance.INSTANCE;
		Error error = manager.errors.get( type );
		return type.cast( error );
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof Errors manager) ) {
			return false;
		}
		return Objects.equals( errors, manager.errors );
	}

	@Override
	public int hashCode() {
		return Objects.hashCode( errors );
	}

	@Override
	public String toString() {
		return "Errors{" +
				"errors=" + errors +
				'}';
	}
}

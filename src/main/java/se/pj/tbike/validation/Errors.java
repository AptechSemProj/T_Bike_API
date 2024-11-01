package se.pj.tbike.validation;

import se.pj.tbike.validation.error.AlreadyExistsError;
import se.pj.tbike.validation.error.EmptyError;
import se.pj.tbike.validation.error.Error;
import se.pj.tbike.validation.error.Error.Builder;
import se.pj.tbike.validation.error.MaximumOverflowError;
import se.pj.tbike.validation.error.MinimumOverflowError;
import se.pj.tbike.validation.error.NoContentError;
import se.pj.tbike.validation.error.NotExistError;
import se.pj.tbike.validation.error.NumberFormatError;
import se.pj.tbike.validation.error.NullValueError;
import se.pj.tbike.validation.error.UnexpectedValueError;
import se.pj.tbike.validation.error.UnexpectedTypeError;
import se.pj.tbike.validation.error.UnknownError;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class Errors {

	private static final class Instance {
		public static final Errors INSTANCE = new Errors();

		static {
			Errors.register( AlreadyExistsError.builder() );
			Errors.register( NotExistError.builder() );
			Errors.register( NoContentError.builder() );
			Errors.register( MaximumOverflowError.builder() );
			Errors.register( MinimumOverflowError.builder() );
			Errors.register( NullValueError.builder() );
			Errors.register( NumberFormatError.builder() );
			Errors.register( UnexpectedValueError.builder() );
			Errors.register( UnexpectedTypeError.builder() );
			Errors.register( UnknownError.builder() );
		}
	}

	private final Map<Class<?>, Builder<?>> builders;

	private Errors() {
		this.builders = new LinkedHashMap<>();
	}

	public static <E extends Error> void register(Builder<E> builder) {
		if ( builder == null ) {
			throw new IllegalArgumentException();
		}
		E error = builder.build();
		Instance.INSTANCE.builders.put( error.getClass(), builder );
	}

	public static <E extends Error> Builder<E> getBuilder(Class<E> type) {
		Map<Class<?>, Builder<?>> builders = Instance.INSTANCE.builders;
		@SuppressWarnings("unchecked")
		Builder<E> builder = (Builder<E>) builders.get( type );
		return builder;
	}

	public static <E extends Error> E get(Class<E> type) {
		Builder<E> builder = getBuilder( type );
		return builder.build();
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof Errors that) ) {
			return false;
		}
		return Objects.equals( builders, that.builders );
	}

	@Override
	public int hashCode() {
		return Objects.hashCode( builders );
	}

	@Override
	public String toString() {
		return "[ Errors ] -- " +
				builders.values()
						.stream()
						.map( Builder::build );
	}
}

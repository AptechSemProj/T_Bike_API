package com.ank.japi.validation;

import com.ank.japi.validation.error.AlreadyExistsError;
import com.ank.japi.validation.ValidationError.Builder;
import com.ank.japi.validation.error.MaximumOverflowError;
import com.ank.japi.validation.error.MinimumOverflowError;
import com.ank.japi.validation.error.NegativeNumberError;
import com.ank.japi.validation.error.NoContentError;
import com.ank.japi.validation.error.NotExistError;
import com.ank.japi.validation.error.NumberFormatError;
import com.ank.japi.validation.error.PositiveNumberError;
import com.ank.japi.validation.error.UnexpectedValueError;
import com.ank.japi.validation.error.UnexpectedTypeError;
import com.ank.japi.validation.error.UnknownError;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class Errors {

    private static final class Instance {
        public static final Errors INSTANCE = new Errors();

        static {
            Errors.register( AlreadyExistsError.builder() );
            Errors.register( MaximumOverflowError.builder() );
            Errors.register( MinimumOverflowError.builder() );
            Errors.register( NegativeNumberError.builder() );
            Errors.register( NoContentError.builder() );
            Errors.register( NotExistError.builder() );
            Errors.register( NumberFormatError.builder() );
            Errors.register( PositiveNumberError.builder() );
            Errors.register( UnexpectedValueError.builder() );
            Errors.register( UnexpectedTypeError.builder() );
            Errors.register( UnknownError.builder() );
        }
    }

    private final Map<Class<?>, Builder<?>> builders;

    private Errors() {
        this.builders = new LinkedHashMap<>();
    }

    public static <E extends ValidationError> void register(Builder<E> builder) {
        if ( builder == null ) {
            throw new IllegalArgumentException();
        }
        E error = builder.build();
        Instance.INSTANCE.builders.put( error.getClass(), builder );
    }

    public static <E extends ValidationError> Builder<E> getBuilder(Class<E> type) {
        Map<Class<?>, Builder<?>> builders = Instance.INSTANCE.builders;
        @SuppressWarnings("unchecked")
        Builder<E> builder = (Builder<E>) builders.get( type );
        return builder;
    }

    public static <E extends ValidationError> E get(Class<E> type) {
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

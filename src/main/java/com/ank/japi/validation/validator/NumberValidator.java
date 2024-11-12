package com.ank.japi.validation.validator;

import com.ank.japi.validation.Validator;

import java.util.Objects;
import java.util.Set;

import java.math.BigDecimal;
import java.math.BigInteger;

@Deprecated
public sealed class NumberValidator<N>
        extends Validator
        permits IntValidator,
                LongValidator {

    public static final Set<Class<?>> STANDARD_NUMBER_TYPES = Set.of(
            Byte.class, Short.class, Integer.class, Long.class,
            Float.class, Double.class,
            BigInteger.class, BigDecimal.class
    );

    private final Class<N> numberType;

    protected NumberValidator(Class<N> type) {
        if ( !STANDARD_NUMBER_TYPES.contains( type ) ) {
            throw new UnsupportedOperationException();
        }
        this.numberType = type;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( !(o instanceof NumberValidator<?> that) ) {
            return false;
        }
        if ( !super.equals( o ) ) {
            return false;
        }
        return Objects.equals( numberType, that.numberType );
    }

    @Override
    public int hashCode() {
        return Objects.hash( super.hashCode(), numberType );
    }
}

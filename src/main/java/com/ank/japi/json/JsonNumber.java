package com.ank.japi.json;

import com.ank.japi.exception.NotAssignableException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.NoSuchElementException;

public class JsonNumber
        implements Json {

    private final boolean nullable;
    private final boolean onlyInteger;
    private final boolean onlyFloat;
    private       Number  value;

    public JsonNumber(
            boolean nullable, boolean onlyInteger, boolean onlyFloat
    ) {
        this.nullable = nullable;
        this.onlyInteger = onlyInteger;
        this.onlyFloat = onlyFloat;
    }

    @Override
    public boolean isAssignable(Object o) {
        if ( o == null ) {
            return nullable;
        }
        if ( onlyFloat ) {
            return o instanceof Float
                    || o instanceof Double
                    || o instanceof BigDecimal;
        }
        if ( onlyInteger ) {
            return o instanceof Byte
                    || o instanceof Short
                    || o instanceof Integer
                    || o instanceof Long
                    || o instanceof BigInteger;
        }
        return o instanceof Number && o instanceof Comparable;
    }

    @Override
    public Number get() throws NoSuchElementException {
        if ( isAssignable( value ) ) {
            return value;
        }
        throw new NoSuchElementException();
    }

    @Override
    public void set(Object o) throws NotAssignableException {
        if ( !isAssignable( o ) ) {
            throw new NotAssignableException();
        }
        this.value = (Number) o;
    }
}

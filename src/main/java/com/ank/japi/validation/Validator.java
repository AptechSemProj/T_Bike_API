package com.ank.japi.validation;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Validator {

    private final List<Requirement> requirements;

    public Validator() {
        this.requirements = new LinkedList<>();
    }

    public final void accept(Requirement... rs) {
        if (rs == null) {
            return;
        }
        for ( Requirement r : rs ) {
            if ( r != null ) {
                this.requirements.add( r );
            }
        }
    }

    public ValidationResult validate(final Object value) {
        var val = new AtomicReference<>( value );
        for ( Requirement r : requirements ) {
            if ( !r.resolve( val.get(), val::set ) ) {
                return ValidationResult.failure( r.getError() );
            }
        }
        return ValidationResult.success( val.get() );
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( !(o instanceof Validator that) ) {
            return false;
        }
        return Objects.equals( requirements, that.requirements );
    }

    @Override
    public int hashCode() {
        return Objects.hashCode( requirements );
    }
}

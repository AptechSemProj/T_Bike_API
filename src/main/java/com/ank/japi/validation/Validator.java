package com.ank.japi.validation;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicReference;

public class Validator {

    private final Set<Requirement> requirements;

    public Validator() {
        this.requirements = new TreeSet<>();
    }

    public final int accept(Requirement... rs) {
        if ( rs == null || rs.length == 0 ) {
            return 0;
        }
        for ( Requirement r : rs ) {
            if ( r != null ) {
                this.requirements.add( r );
            }
        }
        return this.requirements.size();
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

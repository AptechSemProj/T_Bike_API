package com.ank.japi.validation;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public final class ValidationResult {

    private final ValidationError error;
    private final Object          value;

    private ValidationResult(ValidationError err, Object value) {
        this.error = err;
        this.value = value;
    }

    public static ValidationResult success(Object value) {
        return new ValidationResult( null, value );
    }

    public static ValidationResult failure(ValidationError error, Object value) {
        if ( error == null ) {
            throw new NullPointerException( "error is null" );
        }
        return new ValidationResult( error, value );
    }

    public static ValidationResult failure(ValidationError error) {
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
    public String toString() {
        Class<?> valueType = value != null
                             ? value.getClass()
                             : Void.class;
        String strValue = "value = %s(%s)"
                .formatted( valueType.getSimpleName(), value );
        if ( isOk() ) {
            String tmpl = "-- [ Successful ] -- [ %s ]";
            return tmpl.formatted( strValue );
        }
        else {
            String tmpl = "-- [ Failed ] -- [ error = %s, %s ]";
            return tmpl.formatted( error, strValue );
        }
    }
}

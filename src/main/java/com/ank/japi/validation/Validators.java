package com.ank.japi.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

public final class Validators {

    private final Map<String, KeyValue> bindings;

    public Validators() {
        bindings = new HashMap<>();
    }

    public KeyValue bind(String key, Object value) {
        return bindings.computeIfAbsent( key, k -> new KeyValue( value ) );
    }

//    public Validators requireNotNull(KeyValue... pairs) {
//        for ( KeyValue pair : pairs ) {
//            if ( pair != null ) {
//                pair.validator.accept( Requirements.notNull() );
//            }
//        }
//        return this;
//    }
//
//    public Validators requireNotNull(String... keys) {
//        for ( String key : keys ) {
//            KeyValue pair = bindings.get( key );
//            if ( pair != null ) {
//                pair.validator.accept( Requirements.notNull() );
//            }
//        }
//        return this;
//    }

    public Map<String, Object> validate(Consumer<ValidationError> err) {
        Map<String, Object> results = new HashMap<>();
        for ( Entry<String, KeyValue> e : bindings.entrySet() ) {
            ValidationResult result = e.getValue().validate();
            if ( result.isFailed() ) {
                if ( err != null ) {
                    err.accept( result.getError() );
                    return results;
                }
                throw result.getError();
            }
            results.put( e.getKey(), result.getValue() );
        }
        return results;
    }

    public Map<String, Object> validate() {
        return validate( null );
    }

    public static class KeyValue {

        private final Object    value;
        private       Validator validator;

        private KeyValue(Object value) {
            this.value = value;
            this.validator = new Validator();
        }

        @Deprecated
        public KeyValue setValidator(Validator validator) {
            this.validator = validator;
            return this;
        }

        public void require(Requirement... rs) {
            this.validator.accept( rs );
        }

        public ValidationResult validate() {
            return validator.validate( value );
        }
    }
}

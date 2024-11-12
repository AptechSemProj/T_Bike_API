//package com.ank.japi.json;
//
//import com.ank.japi.util.ArraySupport;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class JsonWriter {
//
//    private static final String KEY_VAL_FORMAT = "\"%s\": %s";
//
//    private final Json json;
//
//    public JsonWriter(Json json) {
//        this.json = json;
//    }
//
//    protected final Object getJson() {
//        return json.get();
//    }
//
//    protected String writeNullField(String name) {
//        return String.format( KEY_VAL_FORMAT, name, null );
//    }
//
//    protected String writeStringField(String name, String value) {
//        if ( value == null ) {
//            return writeNullField( name );
//        }
//        else {
//            return String.format( KEY_VAL_FORMAT, name, "\"" + value + "\"" );
//        }
//    }
//
//    protected String writeNumberField(String name, Number value) {
//        if ( value == null ) {
//            return writeNullField( name );
//        }
//        else {
//            return String.format( KEY_VAL_FORMAT, name, value );
//        }
//    }
//
//    protected String writeBooleanField(String name, Boolean value) {
//        if ( value == null ) {
//            return writeNullField( name );
//        }
//        else {
//            return String.format(
//                    KEY_VAL_FORMAT, name, value ? "true" : "false"
//            );
//        }
//    }
//
//    protected String writeObjectField(String name, Map<?, ?> value) {
//        if ( value == null ) {
//            return writeNullField( name );
//        }
//        else {
//            StringBuilder sb = new StringBuilder();
//            sb.append( '{' );
//            for ( Map.Entry<?, ?> entry : value.entrySet() ) {
//                String n = String.valueOf( entry.getKey() );
//                Object v = entry.getValue();
//                if ( v == null ) {
//                    sb.append( writeNullField( n ) );
//                }
//                else {
//                    sb.append( writeFieldByJsonType(
//                            JsonType.fromJavaType( v.getClass() ), n, v
//                    ) );
//                }
//            }
//            sb.append( '}' );
//            return String.format( KEY_VAL_FORMAT, name, sb );
//        }
//    }
//
//    protected String writeArrayField(String name, Object value) {
//        if ( value == null ) {
//            return writeNullField( name );
//        }
//        else {
//            StringBuilder sb = new StringBuilder();
//            sb.append( '[' );
//            List<Object> list = new ArrayList<>();
//            ArraySupport.foreach( value, list::add );
//            String arrStr = String.join(
//                    ",",
//                    list.parallelStream()
//                        .map( o -> {
//                            if ( o == null ) {
//                                return "null";
//                            }
//                            return "";
//                        } )
//                        .toList()
//            );
//            sb.append( arrStr );
//            sb.append( ']' );
//            return String.format( KEY_VAL_FORMAT, name, sb );
//        }
//    }
//
//    private String writeField(JsonField field, Object value) {
//        if ( !field.isAssignable( value ) ) {
//            throw new IllegalArgumentException(
//                    String.format(
//                            "%s is not assignable to %s",
//                            value, field.getJsonType().name()
//                    )
//            );
//        }
//        return writeFieldByJsonType(
//                field.getJsonType(), field.getName(), value );
//    }
//
//    private String writeFieldByJsonType(
//            JsonType type, String name, Object value
//    ) {
//        switch ( type ) {
//            case STRING:
//                return writeStringField( name, (String) value );
//            case NUMBER:
//                return writeNumberField( name, (Number) value );
//            case BOOLEAN:
//                return writeBooleanField( name, (Boolean) value );
//            case ARRAY:
//                return writeArrayField( name, value );
//            case OBJECT:
//                return writeObjectField( name, (Map<?, ?>) value );
//            case ANY:
//            default:
//                if ( value == null ) {
//                    return writeNullField( name );
//                }
//                else {
//                    return writeFieldByJsonType(
//                            JsonType.fromJavaType( value.getClass() ),
//                            name,
//                            value
//                    );
//                }
//        }
//    }
//}

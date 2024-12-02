package com.ank.japi;

import com.ank.japi.validation.Requirement;
import com.ank.japi.validation.Requirements;

@Deprecated
public interface QueryParamsWriter {

    void set(String name, Object value, Requirement... requirements);

    default void setString(
            String name, Object value, Requirement... requirements
    ) {
        set( name, String.valueOf( value ), requirements );
    }

    default void setBoolean(
            String name, Object value, Requirement... requirements
    ) {
        String s = String.valueOf( value );
        Boolean b = Boolean.valueOf( s );
        set( name, b, requirements );
    }

    default void setBoolean(
            String name, Object value, boolean exact,
            Requirement... requirements
    ) {
        if ( exact ) {
            if ( value instanceof Boolean b ) {
                setBoolean( name, b, requirements );
            }
            else {
                String s = String.valueOf( value );
                if ( "true".equalsIgnoreCase( s )
                        || "false".equalsIgnoreCase( s ) ) {
                    setBoolean( name, Boolean.valueOf( s ), requirements );
                }
            }
        }
        else {
            setBoolean( name, value, requirements );
        }
    }

    default void setInt(
            String name, Object value, Requirement... requirements
    ) {
        Requirement[] rs = new Requirement[requirements.length + 1];
        rs[0] = Requirements.isInt();
        System.arraycopy( requirements, 0, rs, 1, requirements.length );
        set( name, value, rs );
    }

    default void setLong(
            String name, Object value, Requirement... requirements
    ) {
        Requirement[] rs = new Requirement[requirements.length + 1];
        rs[0] = Requirements.isLong();
        System.arraycopy( requirements, 0, rs, 1, requirements.length );
        set( name, value, rs );
    }

}

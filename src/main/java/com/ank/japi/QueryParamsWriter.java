package com.ank.japi;

import com.ank.japi.validation.Requirement;
import com.ank.japi.validation.Requirements;

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

//	void setByte(String name, Object value, Requirement... requirements);
//
//	void setShort(String name, Object value, Requirement... requirements);

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

//	void setFloat(String name, Object value, Requirement... requirements);
//
//	void setDouble(String name, Object value, Requirement... requirements);

//	void setBigDecimal(String name, Object value, Requirement... requirements);
//
//	void setBigInteger(String name, Object value, Requirement... requirements);

//	void setArray(String name, Object value, Requirement... requirements);
//
//	void setStringArray(String name, Object value, Requirement... requirements);
//
//	void setBooleanArray(String name, Object value, Requirement... requirements);
//
//	void setBooleanArray(String name, Object value, boolean exact, Requirement... requirements);
//
//	void setByteArray(String name, Object value, Requirement... requirements);
//
//	void setShortArray(String name, Object value, Requirement... requirements);
//
//	void setIntArray(String name, Object value, Requirement... requirements);
//
//	void setLongArray(String name, Object value, Requirement... requirements);
//
//	void setFloatArray(String name, Object value, Requirement... requirements);
//
//	void setDoubleArray(String name, Object value, Requirement... requirements);
//
//	void setBigDecimalArray(String name, Object value, Requirement... requirements);
//
//	void setBigIntegerArray(String name, Object value, Requirement... requirements);
}

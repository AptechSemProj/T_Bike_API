package com.ank.japi.validation;

import com.ank.japi.util.ArraySupport;
import com.ank.japi.validation.error.MaximumOverflowError;
import com.ank.japi.validation.error.MinimumOverflowError;
import com.ank.japi.validation.error.NoContentError;
import com.ank.japi.validation.error.NumberFormatError;
import com.ank.japi.validation.error.UnexpectedValueError;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class Requirements {

    private Requirements() {
        throw new IllegalStateException( "Utility class" );
    }

    public static final int ASSERT_IDENTIFIER = 10;

    public static Requirement assertNull() {
        return new Requirement(
                ASSERT_IDENTIFIER,
                Errors.get( UnexpectedValueError.class )
        ) {
            @Override
            public boolean resolve(Object o, Consumer<Object> out) {
                if ( o == null && out != null ) {
                    out.accept( null );
                }
                return o == null;
            }
        };
    }

    public static Requirement notNull() {
        return new Requirement(
                ASSERT_IDENTIFIER,
                Errors.get( NoContentError.class )
        ) {
            @Override
            public boolean resolve(Object o, Consumer<Object> out) {
                if ( o != null && out != null ) {
                    out.accept( o );
                }
                return o != null;
            }
        };
    }

    private static Integer getLength(Object o) {
        if ( o instanceof CharSequence s ) {
            return s.length();
        }
        else if ( o instanceof Collection<?> c ) {
            return c.size();
        }
        else if ( o instanceof Map<?, ?> m ) {
            return m.size();
        }
        else {
            int len = ArraySupport.getLength( o );
            return len < 0 ? null : len;
        }
    }

    public static final int EMPTY_IDENTIFIER = 20;

    public static Requirement assertEmpty() {
        return new Requirement(
                EMPTY_IDENTIFIER,
                Errors.get( UnexpectedValueError.class )
        ) {
            @Override
            public boolean resolve(Object o, Consumer<Object> out) {
                if ( o == null ) {
                    return false;
                }
                Integer len = getLength( o );
                return len != null && len == 0;
            }
        };
    }

    public static Requirement notEmpty(int max) {
        return new Requirement(
                EMPTY_IDENTIFIER,
                Errors.get( NoContentError.class )
        ) {
            @Override
            public boolean resolve(Object o, Consumer<Object> out) {
                if ( o == null ) {
                    return false;
                }
                Integer l = getLength( o );
                boolean resolved = l != null && l > 0 && (max <= 0 || l <= max);
                if ( resolved && out != null ) {
                    out.accept( o );
                }
                return resolved;
            }
        };
    }

    public static Requirement notEmpty() {
        return notEmpty( -1 );
    }

    public static final int BLANK_IDENTIFIER = 30;

    public static Requirement assertBlank() {
        return new Requirement(
                BLANK_IDENTIFIER,
                Errors.get( UnexpectedValueError.class )
        ) {
            @Override
            public boolean resolve(Object o, Consumer<Object> out) {
                return o instanceof CharSequence s
                        && s.chars().allMatch( Character::isWhitespace );
            }
        };
    }

    public static Requirement notBlank(int max) {
        return new Requirement(
                BLANK_IDENTIFIER,
                Errors.get( NoContentError.class )
        ) {
            @Override
            public boolean resolve(Object o, Consumer<Object> out) {
                if ( !(o instanceof CharSequence s) ) {
                    return false;
                }
                if ( s.chars().allMatch( Character::isWhitespace ) ) {
                    return false;
                }
                int len = s.length();
                boolean resolved = (len > 0 && (max <= 0 || len <= max));
                if ( resolved && out != null ) {
                    out.accept( s );
                }
                return resolved;
            }
        };
    }

    public static Requirement notBlank() {
        return notBlank( -1 );
    }

    public static final int NUMBER_IDENTIFIER = 40;

    public static Requirement isInt(boolean allowsNull) {
        return new Requirement(
                NUMBER_IDENTIFIER,
                Errors.get( NumberFormatError.class )
        ) {
            @Override
            public boolean resolve(Object o, Consumer<Object> out) {
                if ( o == null ) {
                    return allowsNull;
                }
                if ( o instanceof Integer i ) {
                    if ( out != null ) {
                        out.accept( i );
                    }
                    return true;
                }
                try {
                    String s = String.valueOf( o );
                    String str = StringUtils.removeAllWhitespace( s );
                    int i = NumberUtils.isHexNumber( str )
                            ? Integer.decode( str )
                            : Integer.parseInt( str );
                    return resolve( i, out );
                }
                catch ( NumberFormatException e ) {
                    return false;
                }
            }
        };
    }

    public static Requirement isInt() {
        return isInt( false );
    }

    public static Requirement isLong(boolean allowsNull) {
        return new Requirement(
                NUMBER_IDENTIFIER,
                Errors.get( NumberFormatError.class )
        ) {
            @Override
            public boolean resolve(Object o, Consumer<Object> out) {
                if ( o == null ) {
                    return allowsNull;
                }
                if ( o instanceof Long l ) {
                    if ( out != null ) {
                        out.accept( l );
                    }
                    return true;
                }
                String s = String.valueOf( o );
                String str = StringUtils.removeAllWhitespace( s );
                if ( NumberUtils.isRealNumber( str ) ) {
                    return false;
                }
                try {
                    long i = NumberUtils.isHexNumber( str )
                             ? Long.decode( str )
                             : Long.parseLong( str );
                    return resolve( i, out );
                }
                catch ( NumberFormatException e ) {
                    return false;
                }
            }
        };
    }

    public static Requirement isLong() {
        return isLong( false );
    }

    private static <N extends Number & Comparable<N>>
    boolean compareNumber(
            Object o, Consumer<Object> out, Requirement isNum,
            Class<N> type, Predicate<N> resolve
    ) {
        var val = new AtomicReference<>( null );
        if ( !isNum.resolve( o, val::set ) ) {
            return false;
        }
        if ( !type.isInstance( val.get() ) ) {
            return false;
        }
        N cur = type.cast( val.get() );
        boolean resolved = resolve.test( cur );
        if ( resolved && out != null ) {
            out.accept( cur );
        }
        return resolved;
    }

    public static final int MAX_IDENTIFIER = 50;

    public static Requirement maxInt(
            int val, boolean allowsNull, boolean inclusive
    ) {
        return new Requirement(
                MAX_IDENTIFIER,
                Errors.get( MaximumOverflowError.class )
        ) {
            @Override
            public boolean resolve(Object o, Consumer<Object> out) {
                return compareNumber(
                        o, out, isInt( allowsNull ), Integer.class,
                        cur -> cur == val ? inclusive : cur < val
                );
            }
        };
    }

    public static Requirement maxInt(int val) {
        return maxInt( val, true, true );
    }

    public static Requirement maxLong(
            long val, boolean allowsNull, boolean inclusive
    ) {
        return new Requirement(
                MAX_IDENTIFIER,
                Errors.get( MaximumOverflowError.class )
        ) {
            @Override
            public boolean resolve(Object o, Consumer<Object> out) {
                return compareNumber(
                        o, out, isLong( allowsNull ), Long.class,
                        cur -> cur == val ? inclusive : cur < val
                );
            }
        };
    }

    public static Requirement maxLong(long val) {
        return maxLong( val, true, true );
    }

    public static final int MIN_IDENTIFIER = 60;

    public static Requirement minInt(
            int val, boolean allowsNull, boolean inclusive
    ) {
        return new Requirement(
                MIN_IDENTIFIER,
                Errors.get( MinimumOverflowError.class )
        ) {
            @Override
            public boolean resolve(Object object, Consumer<Object> out) {
                return compareNumber(
                        object, out, isInt( allowsNull ), Integer.class,
                        cur -> cur == val ? inclusive : cur > val
                );
            }
        };
    }

    public static Requirement minInt(int val) {
        return minInt( val, true, true );
    }

    public static Requirement minLong(
            long val, boolean allowsNull, boolean inclusive
    ) {
        return new Requirement(
                MIN_IDENTIFIER,
                Errors.get( MinimumOverflowError.class )
        ) {
            @Override
            public boolean resolve(Object object, Consumer<Object> out) {
                return compareNumber(
                        object, out, isLong( allowsNull ), Long.class,
                        cur -> cur == val ? inclusive : cur > val
                );
            }
        };
    }

    public static Requirement minLong(long val) {
        return minLong( val, true, true );
    }

    public static Requirement negativeLong(
            boolean allowsNull, boolean inclusiveZero
    ) {
        return maxLong( 0L, allowsNull, inclusiveZero );
    }

    public static Requirement negativeLong(boolean allowsNull) {
        return negativeLong( allowsNull, false );
    }

    public static Requirement negativeLong() {
        return negativeLong( true );
    }

    public static Requirement positiveLong(
            boolean allowsNull, boolean inclusiveZero
    ) {
        return minLong( 0L, allowsNull, inclusiveZero );
    }

    public static Requirement positiveLong(boolean allowsNull) {
        return positiveLong( allowsNull, false );
    }

    public static Requirement positiveLong() {
        return positiveLong( true );
    }

    public static Requirement positiveInt(
            boolean allowsNull, boolean inclusiveZero
    ) {
        return minInt( 0, allowsNull, inclusiveZero );
    }

    public static Requirement positiveInt(boolean allowsNull) {
        return positiveInt( allowsNull, false );
    }

    public static Requirement positiveInt() {
        return positiveInt( true );
    }

    public static Requirement assertLong(long val) {
        return new Requirement(
                ASSERT_IDENTIFIER,
                Errors.get( UnexpectedValueError.class )
        ) {
            @Override
            public boolean resolve(Object object, Consumer<Object> out) {
                final boolean allowsNull = false;
                return compareNumber(
                        object, out, isLong( allowsNull ), Long.class,
                        Predicate.isEqual( val )
                );
            }
        };
    }

    private static class StringUtils {

        public static String removeAllWhitespace(String s) {
            int len = s.length();
            StringBuilder sb = new StringBuilder( len );
            for ( int i = 0; i < len; i++ ) {
                char c = s.charAt( i );
                if ( !Character.isWhitespace( c ) ) {
                    sb.append( c );
                }
            }
            return sb.toString();
        }
    }

    private static class NumberUtils {

        public static boolean isHexNumber(String s) {
            int index;
            if ( s.startsWith( "+" ) || s.startsWith( "-" ) ) {
                index = 1;
            }
            else {
                index = 0;
            }
            return s.startsWith( "0x", index ) ||
                    s.startsWith( "0X", index ) ||
                    s.startsWith( "#", index );
        }

        public static boolean isRealNumber(String s) {
            return s.contains( "." ) ||
                    s.contains( "e" ) ||
                    s.contains( "E" );
        }
    }
}

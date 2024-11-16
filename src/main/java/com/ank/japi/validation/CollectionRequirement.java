package com.ank.japi.validation;

import com.ank.japi.util.ArraySupport;
import com.ank.japi.validation.error.NoContentError;
import com.ank.japi.validation.error.UnexpectedTypeError;
import com.ank.japi.validation.error.UnexpectedValueError;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class CollectionRequirement
        extends Requirement {

    public CollectionRequirement(int identifier, ValidationError error) {
        super( identifier, error );
    }

    public static CollectionRequirement notNull() {
        return new CollectionRequirement(
                1000,
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

    private static int length(Object o) {
        if ( o instanceof Collection<?> c ) {
            return c.size();
        }
        else if ( o instanceof Map<?, ?> m ) {
            return m.size();
        }
        else {
            return ArraySupport.getLength( o );
        }
    }

    public static CollectionRequirement notEmpty(int maximum) {
        return new CollectionRequirement(
                1000,
                Errors.get( NoContentError.class )
        ) {
            @Override
            public boolean resolve(Object o, Consumer<Object> out) {
                if ( o == null ) {
                    return false;
                }
                int l = length( o );
                if ( l > 0 && (maximum <= 0 || l <= maximum) ) {
                    if ( out != null ) {
                        out.accept( o );
                    }
                    return true;
                }
                return false;
            }
        };
    }

    public static CollectionRequirement notEmpty() {
        return notEmpty( -1 );
    }

    public static CollectionRequirement size(int minimum, int maximum) {
        return new CollectionRequirement(
                1000,
                Errors.get( UnexpectedValueError.class )
        ) {
            @Override
            public boolean resolve(Object o, Consumer<Object> out) {
                if ( o == null ) {
                    return false;
                }
                int l = length( o );
                return l >= 0 && l >= minimum && l <= maximum;
            }
        };
    }

    public static CollectionRequirement size(int size) {
        return size( size, size );
    }

    public static CollectionRequirement maxSize(int maximum) {
        return size( 0, maximum );
    }

    public static CollectionRequirement minSize(int minimum) {
        return size( minimum, Integer.MAX_VALUE );
    }

    public static CollectionRequirement elements() {
        return new CollectionRequirement(
                1005,
                Errors.get( UnexpectedTypeError.class )
        ) {
            @Override
            public boolean resolve(Object o, Consumer<Object> out) {
                if ( o == null ) {
                    return false;
                }
                if ( o instanceof Collection<?> c ) {
                    out.accept( c );
                    return true;
                }
                else if ( o instanceof Map<?, ?> m ) {
                    out.accept( m );
                    return true;
                }
                Object[] arr = ArraySupport.elements( o );
                if ( arr != null ) {
                    out.accept( arr );
                    return true;
                }
                return false;
            }
        };
    }

    public CollectionRequirement count(
            long count, Predicate<Object> predicate
    ) {
        CollectionRequirement before = this;
        return new CollectionRequirement(
                1010,
                Errors.get( UnexpectedValueError.class )
        ) {
            @Override
            public boolean resolve(Object o, Consumer<Object> out) {
                var val = new AtomicReference<>( null );
                if ( !before.resolve( o, val::set ) ) {
                    return false;
                }
                Object v = val.get();
                if ( v instanceof Collection<?> c ) {
                    if ( c.parallelStream().filter( predicate )
                          .count() == count ) {
                        out.accept( c );
                        return true;
                    }
                    return false;
                }
                else if ( v instanceof Map<?, ?> m ) {
                    if ( m.entrySet().parallelStream().filter( predicate )
                          .count() == count ) {
                        out.accept( m );
                        return true;
                    }
                    return false;
                }
                Object[] arr = ArraySupport.elements( o );
                if ( arr != null && Stream.of( arr ).filter( predicate )
                                          .count() == count ) {
                    out.accept( arr );
                    return true;
                }
                return false;
            }
        };
    }

    public CollectionRequirement allMatch(Predicate<Object> predicate) {
        CollectionRequirement before = this;
        return new CollectionRequirement(
                1015,
                Errors.get( UnexpectedValueError.class )
        ) {
            @Override
            public boolean resolve(Object o, Consumer<Object> out) {
                var val = new AtomicReference<>( null );
                if ( !before.resolve( o, val::set ) ) {
                    return false;
                }
                Object v = val.get();
                if ( v instanceof Collection<?> c ) {
                    if ( c.stream().parallel().allMatch( predicate ) ) {
                        out.accept( c );
                        return true;
                    }
                    return false;
                }
                else if ( v instanceof Map<?, ?> m ) {
                    if ( m.entrySet().stream().parallel()
                          .allMatch( predicate ) ) {
                        out.accept( m );
                        return true;
                    }
                    return false;
                }
                if ( ArraySupport.allMatch( v, predicate ) ) {
                    out.accept( v );
                    return true;
                }
                return false;
            }
        };
    }

    public CollectionRequirement anyMatch(Predicate<Object> predicate) {
        CollectionRequirement before = this;
        return new CollectionRequirement(
                1020,
                Errors.get( UnexpectedValueError.class )
        ) {
            @Override
            public boolean resolve(Object o, Consumer<Object> out) {
                var val = new AtomicReference<>( null );
                if ( !before.resolve( o, val::set ) ) {
                    return false;
                }
                Object v = val.get();
                if ( v instanceof Collection<?> c ) {
                    if ( c.stream().parallel().anyMatch( predicate ) ) {
                        out.accept( c );
                        return true;
                    }
                    return false;
                }
                else if ( v instanceof Map<?, ?> m ) {
                    if ( m.entrySet().stream().parallel()
                          .anyMatch( predicate ) ) {
                        out.accept( m );
                        return true;
                    }
                    return false;
                }
                if ( ArraySupport.anyMatch( v, predicate ) ) {
                    out.accept( v );
                    return true;
                }
                return false;
            }
        };
    }
}

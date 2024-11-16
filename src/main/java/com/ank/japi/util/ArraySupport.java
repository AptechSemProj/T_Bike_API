package com.ank.japi.util;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ArraySupport {

    public static Object[] elements(Object o) {
        if ( o instanceof Object[] ) {
            return (Object[]) o;
        }
        else if ( o instanceof byte[] ) {
            int l = ((byte[]) o).length;
            Byte[] arr = new Byte[l];
            for ( int i = 0; i < l; i++ ) {
                arr[i] = ((byte[]) o)[i];
            }
            return arr;
        }
        else if ( o instanceof short[] ) {
            int l = (((short[]) o).length);
            Short[] arr = new Short[l];
            for ( int i = 0; i < l; i++ ) {
                arr[i] = (((short[]) o)[i]);
            }
            return arr;
        }
        else if ( o instanceof int[] ) {
            int l = (((int[]) o).length);
            Integer[] arr = new Integer[l];
            for ( int i = 0; i < l; i++ ) {
                arr[i] = ((int[]) o)[i];
            }
            return arr;
        }
        else if ( o instanceof long[] ) {
            int l = (((long[]) o).length);
            Long[] arr = new Long[l];
            for ( int i = 0; i < l; i++ ) {
                arr[i] = ((long[]) o)[i];
            }
            return arr;
        }
        else if ( o instanceof float[] ) {
            int l = (((float[]) o).length);
            Float[] arr = new Float[l];
            for ( int i = 0; i < l; i++ ) {
                arr[i] = ((float[]) o)[i];
            }
            return arr;
        }
        else if ( o instanceof double[] ) {
            int l = (((double[]) o).length);
            Double[] arr = new Double[l];
            for ( int i = 0; i < l; i++ ) {
                arr[i] = ((double[]) o)[i];
            }
            return arr;
        }
        else if ( o instanceof boolean[] ) {
            int l = (((boolean[]) o).length);
            Boolean[] arr = new Boolean[l];
            for ( int i = 0; i < l; i++ ) {
                arr[i] = ((boolean[]) o)[i];
            }
            return arr;
        }
        else if ( o instanceof char[] ) {
            int l = (((char[]) o).length);
            Character[] arr = new Character[l];
            for ( int i = 0; i < l; i++ ) {
                arr[i] = ((char[]) o)[i];
            }
            return arr;
        }
        return null;
    }

    public static boolean allMatch(Object o, Predicate<Object> predicate) {
        if ( o instanceof Object[] ) {
            for ( Object obj : (Object[]) o ) {
                if ( !predicate.test( obj ) ) {
                    return false;
                }
            }
        }
        else if ( o instanceof byte[] ) {
            for ( byte obj : (byte[]) o ) {
                if ( !predicate.test( obj ) ) {
                    return false;
                }
            }
        }
        else if ( o instanceof short[] ) {
            for ( short obj : (short[]) o ) {
                if ( !predicate.test( obj ) ) {
                    return false;
                }
            }
        }
        else if ( o instanceof int[] ) {
            for ( int obj : (int[]) o ) {
                if ( !predicate.test( obj ) ) {
                    return false;
                }
            }
        }
        else if ( o instanceof long[] ) {
            for ( long obj : (long[]) o ) {
                if ( !predicate.test( obj ) ) {
                    return false;
                }
            }
        }
        else if ( o instanceof float[] ) {
            for ( float obj : (float[]) o ) {
                if ( !predicate.test( obj ) ) {
                    return false;
                }
            }
        }
        else if ( o instanceof double[] ) {
            for ( double obj : (double[]) o ) {
                if ( !predicate.test( obj ) ) {
                    return false;
                }
            }
        }
        else if ( o instanceof boolean[] ) {
            for ( boolean obj : (boolean[]) o ) {
                if ( !predicate.test( obj ) ) {
                    return false;
                }
            }
        }
        else if ( o instanceof char[] ) {
            for ( char obj : (char[]) o ) {
                if ( !predicate.test( obj ) ) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean anyMatch(Object o, Predicate<Object> predicate) {
        if ( o instanceof Object[] ) {
            for ( Object obj : (Object[]) o ) {
                if ( predicate.test( obj ) ) {
                    return true;
                }
            }
        }
        else if ( o instanceof byte[] ) {
            for ( byte obj : (byte[]) o ) {
                if ( predicate.test( obj ) ) {
                    return true;
                }
            }
        }
        else if ( o instanceof short[] ) {
            for ( short obj : (short[]) o ) {
                if ( predicate.test( obj ) ) {
                    return true;
                }
            }
        }
        else if ( o instanceof int[] ) {
            for ( int obj : (int[]) o ) {
                if ( predicate.test( obj ) ) {
                    return true;
                }
            }
        }
        else if ( o instanceof long[] ) {
            for ( long obj : (long[]) o ) {
                if ( predicate.test( obj ) ) {
                    return true;
                }
            }
        }
        else if ( o instanceof float[] ) {
            for ( float obj : (float[]) o ) {
                if ( predicate.test( obj ) ) {
                    return true;
                }
            }
        }
        else if ( o instanceof double[] ) {
            for ( double obj : (double[]) o ) {
                if ( predicate.test( obj ) ) {
                    return true;
                }
            }
        }
        else if ( o instanceof boolean[] ) {
            for ( boolean obj : (boolean[]) o ) {
                if ( predicate.test( obj ) ) {
                    return true;
                }
            }
        }
        else if ( o instanceof char[] ) {
            for ( char obj : (char[]) o ) {
                if ( predicate.test( obj ) ) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void forEach(Object o, Consumer<Object> action) {
        if ( o instanceof Object[] ) {
            for ( Object obj : (Object[]) o ) {
                action.accept( obj );
            }
        }
        else if ( o instanceof byte[] ) {
            for ( byte obj : (byte[]) o ) {
                action.accept( obj );
            }
        }
        else if ( o instanceof short[] ) {
            for ( short obj : (short[]) o ) {
                action.accept( obj );
            }
        }
        else if ( o instanceof int[] ) {
            for ( int obj : (int[]) o ) {
                action.accept( obj );
            }
        }
        else if ( o instanceof long[] ) {
            for ( long obj : (long[]) o ) {
                action.accept( obj );
            }
        }
        else if ( o instanceof float[] ) {
            for ( float obj : (float[]) o ) {
                action.accept( obj );
            }
        }
        else if ( o instanceof double[] ) {
            for ( double obj : (double[]) o ) {
                action.accept( obj );
            }
        }
        else if ( o instanceof boolean[] ) {
            for ( boolean obj : (boolean[]) o ) {
                action.accept( obj );
            }
        }
        else if ( o instanceof char[] ) {
            for ( char obj : (char[]) o ) {
                action.accept( obj );
            }
        }
    }

    public static boolean isArray(Object o) {
        return getLength( o ) >= 0;
    }

    public static int getLength(Object o) {
        if ( o instanceof Object[] ) {
            return ((Object[]) o).length;
        }
        else if ( o instanceof byte[] ) {
            return ((byte[]) o).length;
        }
        else if ( o instanceof short[] ) {
            return ((short[]) o).length;
        }
        else if ( o instanceof int[] ) {
            return ((int[]) o).length;
        }
        else if ( o instanceof long[] ) {
            return ((long[]) o).length;
        }
        else if ( o instanceof float[] ) {
            return ((float[]) o).length;
        }
        else if ( o instanceof double[] ) {
            return ((double[]) o).length;
        }
        else if ( o instanceof boolean[] ) {
            return ((boolean[]) o).length;
        }
        else if ( o instanceof char[] ) {
            return ((char[]) o).length;
        }
        else {
            return -1;
        }
    }
}

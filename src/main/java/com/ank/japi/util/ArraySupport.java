package com.ank.japi.util;

import java.util.function.Consumer;

public class ArraySupport {

    public static void foreach(Object o, Consumer<Object> action) {
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

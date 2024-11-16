package com.ank.japi.impl;

import com.ank.japi.QueryParamsReader;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public final class SimpleQueryParamsReader
        implements QueryParamsReader {

    private final Map<String, Object> params;

    public SimpleQueryParamsReader(Map<String, Object> params) {
        this.params = Map.copyOf( params );
    }

    @Override
    public Object get(String name, Object defaultValue) {
        Object value = params.get( name );
        return value == null ? defaultValue : value;
    }

    @Override
    public Boolean getBoolean(String key) {
        return get( key ) instanceof Boolean b ? b : null;
    }

    @Override
    public Byte getByte(String key) {
        return get( key ) instanceof Byte b ? b : null;
    }

    @Override
    public Short getShort(String key) {
        return get( key ) instanceof Short s ? s : null;
    }

    @Override
    public Integer getInt(String key) {
        return get( key ) instanceof Integer i ? i : null;
    }

    @Override
    public Long getLong(String key) {
        return get( key ) instanceof Long l ? l : null;
    }

    @Override
    public Float getFloat(String key) {
        return get( key ) instanceof Float f ? f : null;
    }

    @Override
    public Double getDouble(String key) {
        return get( key ) instanceof Double d ? d : null;
    }

    @Override
    public BigDecimal getBigDecimal(String key) {
        return get( key ) instanceof BigDecimal bd ? bd : null;
    }

    @Override
    public BigInteger getBigInteger(String key) {
        return get( key ) instanceof BigInteger bi ? bi : null;
    }

//	public Object[] getArray(String key) {
//		Object o = get( key );
//		if ( o instanceof Object[] arr ) {
//			return arr;
//		}
//		if ( o instanceof byte[] obj ) {
//			int len = obj.length;
//			Byte[] arr = new Byte[len];
//			for ( int i = 0; i < len; i++ ) {
//				arr[i] = obj[i];
//			}
//			return arr;
//		}
//		if ( o instanceof short[] obj ) {
//			int len = obj.length;
//			Short[] arr = new Short[len];
//			for ( int i = 0; i < len; i++ ) {
//				arr[i] = obj[i];
//			}
//			return arr;
//		}
//		if ( o instanceof int[] obj ) {
//			int len = obj.length;
//			Integer[] arr = new Integer[len];
//			for ( int i = 0; i < len; i++ ) {
//				arr[i] = obj[i];
//			}
//			return arr;
//		}
//		if ( o instanceof long[] obj ) {
//			int len = obj.length;
//			Long[] arr = new Long[len];
//			for ( int i = 0; i < len; i++ ) {
//				arr[i] = obj[i];
//			}
//			return arr;
//		}
//		if ( o instanceof float[] obj ) {
//			int len = obj.length;
//			Float[] arr = new Float[len];
//			for ( int i = 0; i < len; i++ ) {
//				arr[i] = obj[i];
//			}
//			return arr;
//		}
//		if ( o instanceof double[] obj ) {
//			int len = obj.length;
//			Double[] arr = new Double[len];
//			for ( int i = 0; i < len; i++ ) {
//				arr[i] = obj[i];
//			}
//			return arr;
//		}
//		if ( o instanceof boolean[] obj ) {
//			int len = obj.length;
//			Boolean[] arr = new Boolean[len];
//			for ( int i = 0; i < len; i++ ) {
//				arr[i] = obj[i];
//			}
//			return arr;
//		}
//		if ( o instanceof char[] obj ) {
//			int len = obj.length;
//			String[] arr = new String[len];
//			for ( int i = 0; i < len; i++ ) {
//				arr[i] = Character.toString( obj[i] );
//			}
//			return arr;
//		}
//		return new Object[0];
//	}
//
//	public Boolean[] getBooleanArray(String key) {
//		Object[] o = getArray( key );
//		if ( o instanceof Boolean[] arr ) {
//			return arr;
//		} else {
//			return new Boolean[0];
//		}
//	}
//
//	public String[] getStringArray(String key) {
//		Object[] o = getArray( key );
//		if ( o instanceof String[] arr ) {
//			return arr;
//		} else {
//			return new String[0];
//		}
//	}
//
//	public BigDecimal[] getBigDecimalArray(String key) {
//		Object[] o = getArray( key );
//		if ( o instanceof BigDecimal[] arr ) {
//			return arr;
//		} else {
//			return new BigDecimal[0];
//		}
//	}
//
//	public Double[] getDoubleArray(String key) {
//		Object[] o = getArray( key );
//		if ( o instanceof Double[] arr ) {
//			return arr;
//		} else {
//			return new Double[0];
//		}
//	}
//
//	public Float[] getFloatArray(String key) {
//		Object[] o = getArray( key );
//		if ( o instanceof Float[] arr ) {
//			return arr;
//		} else {
//			return new Float[0];
//		}
//	}
//
//	public BigInteger[] getBigIntegerArray(String key) {
//		Object[] o = getArray( key );
//		if ( o instanceof BigInteger[] arr ) {
//			return arr;
//		} else {
//			return new BigInteger[0];
//		}
//	}
//
//	public Long[] getLongArray(String key) {
//		Object[] o = getArray( key );
//		if ( o instanceof Long[] arr ) {
//			return arr;
//		} else {
//			return new Long[0];
//		}
//	}
//
//	public Integer[] getIntArray(String key) {
//		Object[] o = getArray( key );
//		if ( o instanceof Integer[] is ) {
//			return is;
//		} else {
//			return new Integer[0];
//		}
//	}
//
//	public Short[] getShortArray(String key) {
//		Object[] o = getArray( key );
//		if ( o instanceof Short[] arr ) {
//			return arr;
//		} else {
//			return new Short[0];
//		}
//	}
//
//	public Byte[] getByteArray(String key) {
//		Object[] o = getArray( key );
//		if ( o instanceof Byte[] arr ) {
//			return arr;
//		} else {
//			return new Byte[0];
//		}
//	}
}

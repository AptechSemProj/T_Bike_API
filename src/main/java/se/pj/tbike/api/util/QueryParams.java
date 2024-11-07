package se.pj.tbike.api.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public final class QueryParams {

	private static final BigInteger MAX_LONG =
			BigInteger.valueOf( Long.MAX_VALUE );

	private static final BigInteger MIN_LONG =
			BigInteger.valueOf( Long.MIN_VALUE );

	private static final BigDecimal MAX_DOUBLE =
			new BigDecimal( Double.MAX_VALUE );

	public static final BigDecimal MIN_DOUBLE =
			new BigDecimal( Double.MIN_VALUE );

	private final Map<String, Map.Entry<Class<?>, Object>> params;

	public QueryParams() {
		params = new HashMap<>();
	}

	public void clear() {
		params.clear();
	}

	public <T> void set(String key, T value) {
		if ( value == null ) {
			return;
		}
		Class<?> cls = value.getClass();
		params.put( key, Map.entry( cls, value ) );
	}

	public Object get(String key) {
		Map.Entry<Class<?>, Object> entry = params.get( key );
		if ( entry == null ) {
			return null;
		}
		return entry.getValue();
	}

	public Boolean getBoolean(String key) {
		Object o = get( key );
		if ( o instanceof Boolean b ) {
			return b;
		}
		return Boolean.valueOf( String.valueOf( o ) );
	}

	public String getString(String key) {
		return String.valueOf( get( key ) );
	}

	public BigDecimal getBigDecimal(String key) {
		Object o = get( key );
		if ( o instanceof BigDecimal bd ) {
			return bd;
		} else if ( o instanceof BigInteger bi ) {
			return new BigDecimal( bi );
		} else if ( o instanceof Number n ) {
			return new BigDecimal( n.toString() );
		} else {
			return null;
		}
	}

	public Double getDouble(String key) {
		BigDecimal bd = getBigDecimal( key );
		if ( bd != null &&
				MIN_DOUBLE.compareTo( bd ) <= 0 &&
				bd.compareTo( MAX_DOUBLE ) <= 0 ) {
			return bd.doubleValue();
		}
		return null;
	}

	public Float getFloat(String key) {
		Double d = getDouble( key );
		if ( d != null && Float.MIN_VALUE <= d && d <= Float.MAX_VALUE ) {
			return d.floatValue();
		}
		return null;
	}

	public BigInteger getBigInteger(String key) {
		BigDecimal bd = getBigDecimal( key );
		if ( bd == null ) {
			return null;
		}
		return bd.toBigInteger();
	}

	public Long getLong(String key) {
		BigInteger bi = getBigInteger( key );
		if ( bi != null &&
				MIN_LONG.compareTo( bi ) <= 0 &&
				bi.compareTo( MAX_LONG ) <= 0 ) {
			return bi.longValue();
		}
		return null;
	}

	public Integer getInt(String key) {
		Long l = getLong( key );
		if ( l != null && Integer.MIN_VALUE <= l && l <= Integer.MAX_VALUE ) {
			return l.intValue();
		}
		return null;
	}

	public Short getShort(String key) {
		Long l = getLong( key );
		if ( l != null && Short.MIN_VALUE <= l && l <= Short.MAX_VALUE ) {
			return l.shortValue();
		}
		return null;
	}

	public Byte getByte(String key) {
		Long l = getLong( key );
		if ( l != null && Byte.MIN_VALUE <= l && l <= Byte.MAX_VALUE ) {
			return l.byteValue();
		}
		return null;
	}

	public Object[] getArray(String key) {
		Object o = get( key );
		if ( o instanceof Object[] arr ) {
			return arr;
		}
		if ( o instanceof byte[] obj ) {
			int len = obj.length;
			Byte[] arr = new Byte[len];
			for ( int i = 0; i < len; i++ ) {
				arr[i] = obj[i];
			}
			return arr;
		}
		if ( o instanceof short[] obj ) {
			int len = obj.length;
			Short[] arr = new Short[len];
			for ( int i = 0; i < len; i++ ) {
				arr[i] = obj[i];
			}
			return arr;
		}
		if ( o instanceof int[] obj ) {
			int len = obj.length;
			Integer[] arr = new Integer[len];
			for ( int i = 0; i < len; i++ ) {
				arr[i] = obj[i];
			}
			return arr;
		}
		if ( o instanceof long[] obj ) {
			int len = obj.length;
			Long[] arr = new Long[len];
			for ( int i = 0; i < len; i++ ) {
				arr[i] = obj[i];
			}
			return arr;
		}
		if ( o instanceof float[] obj ) {
			int len = obj.length;
			Float[] arr = new Float[len];
			for ( int i = 0; i < len; i++ ) {
				arr[i] = obj[i];
			}
			return arr;
		}
		if ( o instanceof double[] obj ) {
			int len = obj.length;
			Double[] arr = new Double[len];
			for ( int i = 0; i < len; i++ ) {
				arr[i] = obj[i];
			}
			return arr;
		}
		if ( o instanceof boolean[] obj ) {
			int len = obj.length;
			Boolean[] arr = new Boolean[len];
			for ( int i = 0; i < len; i++ ) {
				arr[i] = obj[i];
			}
			return arr;
		}
		if ( o instanceof char[] obj ) {
			int len = obj.length;
			String[] arr = new String[len];
			for ( int i = 0; i < len; i++ ) {
				arr[i] = Character.toString( obj[i] );
			}
			return arr;
		}
		return new Object[0];
	}

	public Boolean[] getBooleanArray(String key) {
		Object[] o = getArray( key );
		if ( o instanceof Boolean[] arr ) {
			return arr;
		} else {
			return new Boolean[0];
		}
	}

	public String[] getStringArray(String key) {
		Object[] o = getArray( key );
		if ( o instanceof String[] arr ) {
			return arr;
		} else {
			return new String[0];
		}
	}

	public BigDecimal[] getBigDecimalArray(String key) {
		Object[] o = getArray( key );
		if ( o instanceof BigDecimal[] arr ) {
			return arr;
		} else {
			return new BigDecimal[0];
		}
	}

	public Double[] getDoubleArray(String key) {
		Object[] o = getArray( key );
		if ( o instanceof Double[] arr ) {
			return arr;
		} else {
			return new Double[0];
		}
	}

	public Float[] getFloatArray(String key) {
		Object[] o = getArray( key );
		if ( o instanceof Float[] arr ) {
			return arr;
		} else {
			return new Float[0];
		}
	}

	public BigInteger[] getBigIntegerArray(String key) {
		Object[] o = getArray( key );
		if ( o instanceof BigInteger[] arr ) {
			return arr;
		} else {
			return new BigInteger[0];
		}
	}

	public Long[] getLongArray(String key) {
		Object[] o = getArray( key );
		if ( o instanceof Long[] arr ) {
			return arr;
		} else {
			return new Long[0];
		}
	}

	public Integer[] getIntArray(String key) {
		Object[] o = getArray( key );
		if ( o instanceof Integer[] is ) {
			return is;
		} else {
			return new Integer[0];
		}
	}

	public Short[] getShortArray(String key) {
		Object[] o = getArray( key );
		if ( o instanceof Short[] arr ) {
			return arr;
		} else {
			return new Short[0];
		}
	}

	public Byte[] getByteArray(String key) {
		Object[] o = getArray( key );
		if ( o instanceof Byte[] arr ) {
			return arr;
		} else {
			return new Byte[0];
		}
	}

//	public Date getDate(String key) {
//		DateTimeFormatter formatter = F
//		return null;
//	}

}

package se.pj.tbike.validation;

import se.pj.tbike.validation.error.MaximumOverflowError;
import se.pj.tbike.validation.error.MinimumOverflowError;
import se.pj.tbike.validation.error.NoContentError;
import se.pj.tbike.validation.error.NumberFormatError;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public final class Requirements {

	private Requirements() {
		throw new IllegalStateException( "Utility class" );
	}

//	public static Requirement requireNull() {
//		final int identifier = 100;
//		Error error = Errors.get( UnexpectedValueError.class );
//		return new Requirement( identifier, error ) {
//
//			@Override
//			public boolean resolve(Object o, Consumer<Object> out) {
//				boolean resolved = (o == null);
//				if ( resolved && out != null ) {
//					out.accept( null );
//				}
//				return resolved;
//			}
//		};
//	}

	public static final int NOT_NULL_IDENTIFIER = 10;

	public static Requirement notNull() {
		Error error = Errors.get( NoContentError.class );
		return new Requirement( NOT_NULL_IDENTIFIER, error ) {

			@Override
			public boolean resolve(Object o, Consumer<Object> out) {
				boolean resolved = (o != null);
				if ( resolved && out != null ) {
					out.accept( o );
				}
				return resolved;
			}
		};
	}

	public static final int NOT_EMPTY_IDENTIFIER = 20;

	public static Requirement notEmpty(int max) {
		Error error = Errors.get( NoContentError.class );
		return new Requirement( NOT_EMPTY_IDENTIFIER, error ) {

			@Override
			public boolean resolve(Object o, Consumer<Object> out) {
				int len;
				if ( o == null ) {
					return false;
				} else if ( o instanceof CharSequence s ) {
					len = s.length();
				} else if ( o instanceof Collection<?> c ) {
					len = c.size();
				} else if ( o instanceof Map<?, ?> m ) {
					len = m.size();
				} else {
					len = ArrayUtils.getLength( o );
				}
				boolean resolved = (len > 0 && (max <= 0 || len <= max));
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

	public static final int NOT_BLANK_IDENTIFIER = 30;

	public static Requirement notBlank(int max) {
		Error error = Errors.get( NoContentError.class );
		return new Requirement( NOT_BLANK_IDENTIFIER, error ) {

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
		Error error = Errors.get( NumberFormatError.class );
		return new Requirement( NUMBER_IDENTIFIER, error ) {

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
				String s = String.valueOf( o );
				String str = StringUtils.removeAllWhitespace( s );
				if ( NumberUtils.isRealNumber( str ) ) {
					return false;
				}
				try {
					int i = NumberUtils.isHexNumber( str )
							? Integer.decode( str )
							: Integer.parseInt( str );
					return resolve( i, out );
				} catch ( NumberFormatException e ) {
					return false;
				}
			}
		};
	}

	public static Requirement isInt() {
		return isInt( false );
	}

	public static Requirement isLong(boolean allowsNull) {
		Error error = Errors.get( NumberFormatError.class );
		return new Requirement( NUMBER_IDENTIFIER, error ) {

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
				} catch ( NumberFormatException e ) {
					return false;
				}
			}
		};
	}

	public static final int MAX_IDENTIFIER = 50;

	@SuppressWarnings("DuplicatedCode")
	public static Requirement maxLong(long maxValue, boolean allowsNull, boolean inclusive) {
		Error error = Errors.get( MaximumOverflowError.class );
		return new Requirement( MAX_IDENTIFIER, error ) {

			@Override
			public boolean resolve(Object o, Consumer<Object> out) {
				var val = new AtomicReference<>( null );
				if ( !isLong( allowsNull ).resolve( o, val::set ) ) {
					return false;
				}
				if ( !(val.get() instanceof Long cur) ) {
					return false;
				}
				boolean resolved = cur == maxValue
						? inclusive
						: cur < maxValue;
				if ( resolved && out != null ) {
					out.accept( cur );
				}
				return resolved;
			}
		};
	}

	public static Requirement maxLong(long value) {
		return maxLong( value, true, true );
	}

	public static final int MIN_IDENTIFIER = 60;

	@SuppressWarnings("DuplicatedCode")
	public static Requirement minLong(long minValue, boolean allowsNull, boolean inclusive) {
		Error error = Errors.get( MinimumOverflowError.class );
		return new Requirement( MIN_IDENTIFIER, error ) {

			@Override
			public boolean resolve(Object o, Consumer<Object> out) {
				var val = new AtomicReference<>( null );
				if ( !isLong( allowsNull ).resolve( o, val::set ) ) {
					return false;
				}
				if ( !(val.get() instanceof Long cur) ) {
					return false;
				}
				boolean resolved = cur == minValue
						? inclusive
						: cur > minValue;
				if ( resolved && out != null ) {
					out.accept( cur );
				}
				return resolved;
			}
		};
	}

	public static Requirement minLong(long value) {
		return minLong( value, true, true );
	}

	public static Requirement minInt(int minValue, boolean allowsNull, boolean inclusive) {
		Error error = Errors.get( MinimumOverflowError.class );
		return new Requirement( MIN_IDENTIFIER, error ) {

			@Override
			public boolean resolve(Object o, Consumer<Object> out) {
				var val = new AtomicReference<>( null );
				if ( !isInt( allowsNull ).resolve( o, val::set ) ) {
					return false;
				}
				if ( !(val.get() instanceof Integer cur) ) {
					return false;
				}
				boolean resolved = cur == minValue
						? inclusive
						: cur > minValue;
				if ( resolved && out != null ) {
					out.accept( cur );
				}
				return resolved;
			}
		};
	}

	public static Requirement minInt(int value) {
		return minInt( value, true, true );
	}

	public static Requirement negativeLong(boolean allowsNull, boolean inclusiveZero) {
		return maxLong( 0L, allowsNull, inclusiveZero );
	}

	public static Requirement negativeLong(boolean allowsNull) {
		return negativeLong( allowsNull, false );
	}

	public static Requirement negativeLong() {
		return negativeLong( true );
	}

	public static Requirement positiveLong(boolean allowsNull, boolean inclusiveZero) {
		return minLong( 0L, allowsNull, inclusiveZero );
	}

	public static Requirement positiveLong(boolean allowsNull) {
		return positiveLong( allowsNull, false );
	}

	public static Requirement positiveLong() {
		return positiveLong( true );
	}

	public static Requirement positiveInt(boolean allowsNull, boolean inclusiveZero) {
		return minInt( 0, allowsNull, inclusiveZero );
	}

	public static Requirement positiveInt(boolean allowsNull) {
		return positiveInt( allowsNull, false );
	}

	public static Requirement positiveInt() {
		return positiveInt( true );
	}


	private static class ArrayUtils {
		public static int getLength(Object o) {
			if ( o instanceof Object[] arr ) {
				return arr.length;
			} else if ( o instanceof boolean[] arr ) {
				return arr.length;
			} else if ( o instanceof char[] arr ) {
				return arr.length;
			} else if ( o instanceof byte[] arr ) {
				return arr.length;
			} else if ( o instanceof short[] arr ) {
				return arr.length;
			} else if ( o instanceof int[] arr ) {
				return arr.length;
			} else if ( o instanceof long[] arr ) {
				return arr.length;
			} else if ( o instanceof float[] arr ) {
				return arr.length;
			} else if ( o instanceof double[] arr ) {
				return arr.length;
			} else {
				return -1;
			}
		}
	}

	private static class StringUtils {

		@SuppressWarnings("DuplicatedCode")
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

		@SuppressWarnings("DuplicatedCode")
		public static boolean isHexNumber(String s) {
			int index;
			if ( s.startsWith( "+" ) || s.startsWith( "-" ) ) {
				index = 1;
			} else {
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

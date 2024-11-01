package se.pj.tbike.validation.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.pj.tbike.validation.Errors;
import se.pj.tbike.validation.ValidationResult;
import se.pj.tbike.validation.error.MaximumOverflowError;
import se.pj.tbike.validation.error.MinimumOverflowError;
import se.pj.tbike.validation.error.NumberFormatError;
import se.pj.tbike.validation.error.NullValueError;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import java.math.BigDecimal;
import java.math.BigInteger;

public abstract
class NumberValidator<N extends Number & Comparable<N>>
		implements Validator {

	private final NumberFormatError numberFormatError =
			Errors.get( NumberFormatError.class );
	private final MinimumOverflowError minimumOverflowError =
			Errors.get( MinimumOverflowError.class );
	private final MaximumOverflowError maximumOverflowError =
			Errors.get( MaximumOverflowError.class );
	private final NullValueError nullValueError =
			Errors.get( NullValueError.class );

	public static final Set<Class<?>> STANDARD_NUMBER_TYPES = Set.of(
			Byte.class, Short.class, Integer.class, Long.class,
			Float.class, Double.class,
			BigInteger.class, BigDecimal.class
	);

	private final Configuration conf;
	private final N zero;
	private N max, min;
	private boolean excludeZero = false;
	private boolean acceptNull = false;

	public NumberValidator(Class<N> type, N zero) {
		if ( !STANDARD_NUMBER_TYPES.contains( type ) ) {
			throw new UnsupportedOperationException();
		}
		if ( zero == null ) {
			throw new NumberFormatException();
		}
		this.zero = zero;
		this.conf = new Configuration();
	}

	@Override
	public final void configure(Consumer<Configuration> conf) {
		if ( conf != null ) {
			conf.accept( this.conf );
		}
	}

	protected abstract N convertValue(Number n,
	                                  Consumer<MaximumOverflowError> maxErr,
	                                  Consumer<MinimumOverflowError> minErr)
			throws IllegalArgumentException;

	protected abstract N parse(String s)
			throws NumberFormatException;

	protected final boolean isHexNumber(String s) {
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

	private String removeWhitespace(String s) {
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

	private boolean isNumeric(Object o, Consumer<N> out) {
		try {
			if ( o instanceof Number number ) {
				if ( out != null ) {
					N n = convertValue( number, null, null );
					out.accept( n );
				}
				return true;
			} else {
				String str = String.valueOf( o );
				String trimmed = removeWhitespace( str );
				N n = parse( trimmed );
				return isNumeric( n, out );
			}
		} catch ( IllegalArgumentException e ) {
			if ( conf.isAcceptLogging() ) {
				Loggers.LOGGER.warn( e.getMessage() );
			}
			return false;
		}
	}

	public final NumberValidator<N> acceptNull() {
		this.acceptNull = true;
		return this;
	}

	public final NumberValidator<N> acceptMaxValue(N max) {
		this.max = max;
		return this;
	}

	public final NumberValidator<N> acceptMinValue(N min) {
		this.min = min;
		return this;
	}

	public final NumberValidator<N> acceptOnlyNegative() {
		this.max = zero;
		this.excludeZero = true;
		return this;
	}

	public final NumberValidator<N> acceptOnlyPositive() {
		this.min = zero;
		this.excludeZero = true;
		return this;
	}

	@Override
	public final ValidationResult validate(final Object value) {
		if ( value == null ) {
			if ( acceptNull ) {
				return ValidationResult.success( null );
			} else {
				return ValidationResult.failure( nullValueError );
			}
		}
		AtomicReference<N> number = new AtomicReference<>();
		if ( !isNumeric( value, number::set ) ) {
			return ValidationResult.failure( numberFormatError );
		}
		N num = number.get();
		if ( min != null ) {
			// min > num
			boolean lessThanMin = min.compareTo( num ) > 0;
			if ( lessThanMin ) {
				return ValidationResult.failure( minimumOverflowError, num );
			}// min <= num && num == 0 && > 0
			else if ( zero.equals( num ) && excludeZero ) {
				return ValidationResult.failure( minimumOverflowError, zero );
			}
		}
		if ( max != null && max.compareTo( num ) < 0 ) {
			return ValidationResult.failure( maximumOverflowError, num );
		}
		return ValidationResult.success( num );
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof NumberValidator<?> that) ) {
			return false;
		}
		return Objects.equals( numberFormatError, that.numberFormatError ) &&
				Objects.equals( minimumOverflowError, that.minimumOverflowError ) &&
				Objects.equals( maximumOverflowError, that.maximumOverflowError ) &&
//				Objects.equals( numberType, that.numberType ) &&
				Objects.equals( conf, that.conf ) &&
				Objects.equals( max, that.max ) &&
				Objects.equals( min, that.min );
	}

	@Override
	public int hashCode() {
		return Objects.hash( numberFormatError, minimumOverflowError, maximumOverflowError,
//				numberType,
				conf, max, min );
	}

	private static class Loggers {
		private static final Logger LOGGER =
				LoggerFactory.getLogger( NumberValidator.class );
	}
}

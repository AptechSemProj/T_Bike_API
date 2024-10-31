package se.pj.tbike.validation.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.NumberUtils;
import se.pj.tbike.reflect.GenericType;
import se.pj.tbike.validation.Errors;
import se.pj.tbike.validation.ValidationResult;
import se.pj.tbike.validation.error.MaxError;
import se.pj.tbike.validation.error.MinError;
import se.pj.tbike.validation.error.NanError;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public abstract class NumberValidator<N extends Number & Comparable<N>>
		implements Validator {

	private final NanError nanError = Errors.get( NanError.class );
	private final MinError minError = Errors.get( MinError.class );
	private final MaxError maxError = Errors.get( MaxError.class );

	private final Class<N> numberType;
	private Configuration conf;
	private N max, min;

	public NumberValidator() {
		Class<N> type = getNumberType();
		if ( !NumberUtils.STANDARD_NUMBER_TYPES.contains( type ) ) {
			throw new UnsupportedOperationException();
		}
		this.numberType = type;
		this.conf = new Configuration();
	}

	@Override
	public final void applyConf(Configuration conf) {
		this.conf = conf;
	}

	protected Class<N> getNumberType() {
		var genericType = new GenericType<>( this );
		return genericType.getType( 0 );
	}

	private static <N extends Number> N convert(Number n, Class<N> type) {
		return NumberUtils.convertNumberToTargetClass( n, type );
	}

	private static <N extends Number> N parse(String s, Class<N> type) {
		return NumberUtils.parseNumber( s, type );
	}

	private boolean isNumeric(Object o, Consumer<N> out) {
		try {
			if ( o instanceof Number number ) {
				if ( out != null ) {
					out.accept( convert( number, numberType ) );
				}
				return true;
			} else {
				String str = String.valueOf( o );
				Number number = parse( str, Number.class );
				return isNumeric( number, out );
			}
		} catch ( IllegalArgumentException e ) {
			if ( conf.isAcceptLogging() ) {
				Loggers.LOGGER.warn( e.getMessage() );
			}
			return false;
		}
	}

	public final NumberValidator<N> setSize(N min, N max) {
		return this.setMin( min ).setMax( max );
	}

	public final NumberValidator<N> setMax(N max) {
		this.max = max;
		return this;
	}

	public final NumberValidator<N> setMin(N min) {
		this.min = min;
		return this;
	}

	@Override
	public final ValidationResult validate(final Object value) {
		NotNullValidator notNullValidator = new NotNullValidator();
		ValidationResult rs = notNullValidator.validate( value );
		if ( rs.isFailed() ) {
			return rs;
		}
		AtomicReference<N> number = new AtomicReference<>();
		if ( !isNumeric( value, number::set ) ) {
			return ValidationResult.failure( nanError );
		}
		N num = number.get();
		if ( min != null && min.compareTo( num ) > 0 ) {
			return ValidationResult.failure( minError, num );
		}
		if ( max != null && max.compareTo( num ) < 0 ) {
			return ValidationResult.failure( maxError, num );
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
		return Objects.equals( nanError, that.nanError ) &&
				Objects.equals( minError, that.minError ) &&
				Objects.equals( maxError, that.maxError ) &&
				Objects.equals( numberType, that.numberType ) &&
				Objects.equals( conf, that.conf ) &&
				Objects.equals( max, that.max ) &&
				Objects.equals( min, that.min );
	}

	@Override
	public int hashCode() {
		return Objects.hash( nanError, minError, maxError,
				numberType, conf, max, min );
	}

	private static class Loggers {
		private static final Logger LOGGER =
				LoggerFactory.getLogger( NumberValidator.class );
	}
}

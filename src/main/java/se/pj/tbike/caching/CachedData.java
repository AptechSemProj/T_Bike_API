package se.pj.tbike.caching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.Callable;

public class CachedData<V> {

	private V value;

	private final CacheController controller;
	private final Callable<String> releaseReport;

	// package-private
	CachedData(V value, CacheController controller, Callable<String> releaseReport) {
		if ( controller == null ) {
			throw new IllegalArgumentException();
		}
		if ( releaseReport == null ) {
			throw new IllegalArgumentException();
		}
		this.value = value;
		this.controller = CacheController.copy( controller );
		this.releaseReport = releaseReport;
		this.controller.start( this::release );
	}

	public V get() {
		if ( !controller.isStarted() ) {
			controller.start( this::release );
		}
		if ( !isEmpty() ) {
			controller.restart( this::release );
		}
		return value;
	}

	public boolean isEmpty() {
		return value == null;
	}

	public boolean isPresent() {
		return value != null;
	}

	// package-private
	CachedData<V> replace(V m) {
		controller.restart( this::release );
		value = m;
		return this;
	}

	private void release() {
		value = null;
		controller.end();
		try {
			String msg = releaseReport.call();
			Loggers.LOGGER.info( msg );
		} catch ( Exception e ) {
			Loggers.LOGGER.error( e.getMessage() );
			throw new RuntimeException( e );
		}
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof CachedData<?> that) ) {
			return false;
		}
		return Objects.equals( value, that.value ) &&
				Objects.equals( controller, that.controller );
	}

	@Override
	public int hashCode() {
		return Objects.hash( value, controller );
	}

	private static class Loggers {
		public static final Logger LOGGER = LoggerFactory.getLogger( CachedData.class );
	}
}

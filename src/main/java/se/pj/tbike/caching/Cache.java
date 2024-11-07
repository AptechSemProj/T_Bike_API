package se.pj.tbike.caching;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public final class Cache {

	private Map<String, Object> value;

	private final CacheControl controller;
	private final Consumer<Cache> report;

	// package-private
	Cache(Map<String, Object> value, CacheControl controller, Consumer<Cache> report) {
		if ( controller == null ) {
			throw new IllegalArgumentException( "controller cannot be null" );
		}
		if ( report == null ) {
			throw new IllegalArgumentException( "releaseReport cannot be null" );
		}
		this.value = safety( value );
		this.report = report;
		this.controller = CacheControl.copy( controller );
		this.controller.start( this::clear );
	}

	private void clear() {
		report.accept( this );
		if ( isPresent() ) {
			value.clear();
		}
		value = null;
		controller.end();
	}

	public Map<String, Object> get() {
		if ( !controller.isStarted() ) {
			controller.start( this::clear );
		}
		if ( !isNull() ) {
			controller.restart( this::clear );
		}
		return value;
	}

	// package-private
	void set(Map<String, Object> value) {
		if ( isPresent() ) {
			this.value.clear();
		}
		this.value = safety( value );
		this.controller.restart( this::clear );
	}

	public boolean isNull() {
		return value == null;
	}

	public boolean isEmpty() {
		return value != null && value.isEmpty();
	}

	public boolean isPresent() {
		return value != null && !value.isEmpty();
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof Cache that) ) {
			return false;
		}
		return Objects.equals( value, that.value ) &&
				Objects.equals( controller, that.controller ) &&
				Objects.equals( report, that.report );
	}

	@Override
	public int hashCode() {
		return Objects.hash( value, controller, report );
	}

	@Override
	public String toString() {
		return String.valueOf( value );
	}

	private static Map<String, Object> safety(Map<String, Object> value) {
		try {
			if ( value != null ) {
				String tempKey = "test-modify-map";
				value.put( tempKey, tempKey );
				value.remove( tempKey, tempKey );
			}
			return value;
		} catch ( UnsupportedOperationException e ) {
			if ( value.size() > 10 ) {
				return new LinkedHashMap<>( value );
			} else {
				return new HashMap<>( value );
			}
		}
	}
}

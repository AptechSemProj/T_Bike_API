package se.pj.tbike.caching;

import java.util.Objects;

public class CachedData<V> {

	private V value;

	private final CacheController controller;

	// package-private
	CachedData(V value, CacheController controller) {
		if ( controller == null ) {
			throw new IllegalArgumentException();
		}
		this.value = value;
		this.controller = CacheController.copy( controller );
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
	void replace(V m) {
		controller.restart( this::release );
		value = m;
	}

	private void release() {
		value = null;
		controller.end();
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
}

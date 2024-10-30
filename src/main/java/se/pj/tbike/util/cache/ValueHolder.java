package se.pj.tbike.util.cache;

import java.util.Objects;

import java.time.Duration;

// package-private
@Deprecated
class ValueHolder<V> {
	private V value;
	private Duration storedTime;

	ValueHolder(V init) {
		this.value = init;
		resetStoredTime();
	}

	final V get() {
		resetStoredTime();
		return value;
	}

	ValueHolder<V> replace(V v) {
		this.value = v;
		resetStoredTime();
		return this;
	}

	final void resetStoredTime() {
		storedTime = Duration.ofNanos( System.nanoTime() );
	}

	final boolean isExpired(Duration maxTime) {
		Duration now = Duration.ofNanos( System.nanoTime() );
		return storedTime.plus( maxTime ).minus( now )
				.isNegative();
	}

	boolean isNew(V v) {
		return !Objects.equals( value, v );
	}
}

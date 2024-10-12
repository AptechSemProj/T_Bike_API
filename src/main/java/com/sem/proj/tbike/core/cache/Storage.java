package com.sem.proj.tbike.core.cache;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;

import java.time.Duration;

public final class Storage<K extends Comparable<K>, V> {

	private final Map<K, Caching<V>> cached;
	private final Function<V, K> keyProvider;
	private final Duration maxStorageTime;

	public Storage(Function<V, K> keyProvider,
	               Duration maxStorageTime) {
		this.keyProvider = keyProvider;
		this.cached = new TreeMap<>();
		this.maxStorageTime = maxStorageTime;
	}

	public int size() {
		return cached.size();
	}

	public Optional<V> cache(V value) {
		K key = keyProvider.apply(value);
		if (key == null)
			throw new NullPointerException("key is null");
		Caching<V> caching = cached.get(key);
		if (caching == null)
			cached.put(key, new Caching<>(value));
		else if (!Objects.equals(caching.value, value))
			caching.replaceValue(value);
		return Optional.ofNullable(value);
	}

	public Optional<V> get(K key) {
		Caching<V> caching = cached.get(key);
		if (caching == null) {
			cached.put(key, new Caching<>(null));
			return Optional.empty();
		}
		return Optional.ofNullable(caching.useValue());
	}

	public List<V> getRange(int start, int end) {
		if (end < start)
			throw new IllegalArgumentException("end less then start.");
		List<Caching<V>> l = new ArrayList<>(cached.values());
		return new ArrayList<>() {{
			for (int i = start; i < end; i++)
				add(l.get(i).useValue());
		}};
	}

	public boolean isCaching(K key) {
		Caching<V> c = cached.get(key);
		return c != null && c.value != null;
	}

	public boolean isCaching(V value) {
		return isCaching(keyProvider.apply(value));
	}

	public void clean() {
		cached.entrySet().removeIf(
				e -> e.getValue().isExpired(maxStorageTime));
	}

	// package-private
	Duration getMaxStorageTime() {
		return maxStorageTime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Storage<?, ?> storage)) return false;
		return Objects.equals(cached, storage.cached) &&
				Objects.equals(keyProvider, storage.keyProvider);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cached, keyProvider);
	}

	/**
	 * Value wrapper class
	 *
	 * @param <T> type of value
	 */
	private static class Caching<T> {
		private T value;
		private Duration storedTime;

		Caching(T init) {
			this.value = init;
			resetStoredTime();
		}

		T useValue() {
			resetStoredTime();
			return value;
		}

		void replaceValue(T newValue) {
			this.value = newValue;
			resetStoredTime();
		}

		void resetStoredTime() {
			storedTime = Duration.ofNanos(System.nanoTime());
		}

		boolean isExpired(Duration maxTime) {
			Duration now = Duration.ofNanos(System.nanoTime());
			return storedTime.plus(maxTime).minus(now)
					.isNegative();
		}
	}
}

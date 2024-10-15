package se.pj.tbike.util.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import java.time.Duration;

import lombok.Getter;

public class Storage<K extends Comparable<K>, V> {

	private final Map<K, ValueHolder<V>> cached;
	private final Function<V, K> keyMapper;
	@Getter
	private final Duration maxStorageTime;
	@Getter
	private int realSize;

	public Storage( Function<V, K> keyMapper, Duration maxStorageTime ) {
		this.cached = new TreeMap<>();
		this.keyMapper = Objects.requireNonNull(
				keyMapper, "keyMapper is null" );
		this.maxStorageTime = Objects.requireNonNull(
				maxStorageTime, "maxStorageTime is null" );
	}

	public int size() {
		return cached.size();
	}

	public void cache( K key, V value ) {
		if ( key == null )
			throw new NullPointerException( "key is null" );
		ValueHolder<V> p = cached.get( key );
		if ( p == null ) {
			if ( value == null ) {
				cached.put( key, new Placeholder<>() );
				return;
			}
			cached.put( key, new ValueHolder<>( value ) );
			realSize++;
		} else if ( p.isNew( value ) ) {
			if ( value == null ) {
				cached.put( key, new Placeholder<>() );
			} else if ( p instanceof Placeholder<V> ) {
				cached.put( key, p.replace( value ) );
			} else {
				p.replace( value );
			}
		} else {
			p.resetStoredTime();
		}
	}

	public void cache( V value ) {
		cache( keyMapper.apply( value ), value );
	}

	public Optional<V> get( K key ) {
		ValueHolder<V> p = cached.get( key );
		if ( p == null ) {
			cached.put( key, new Placeholder<>() );
			return Optional.empty();
		}
		return Optional.ofNullable( p.get() );
	}

	/**
	 * @param start include
	 * @param end   exclude
	 * @return list of value.
	 */
	public List<V> valuesBetween( int start, int end ) {
		if ( start < 0 )
			throw new IndexOutOfBoundsException( "start less than 0" );
		if ( end < start )
			throw new IllegalArgumentException( "start greater than end" );

		int capacity = Math.min( end, realSize ) - start;
		if ( capacity > 0 ) {
			List<V> list = new ArrayList<>( capacity );
			List<ValueHolder<V>> values = new ArrayList<>( cached.values() );
			ValueHolder<V> value;
			int idx = start;
			for ( int i = 0; i < capacity; idx++ ) {
				value = values.get( idx );
				if ( !( value instanceof Placeholder<V> ) ) {
					list.add( value.get() );
					i++;
				}
			}
			return Collections.unmodifiableList( list );
		}
		return List.of();
	}

	public List<V> valuesFrom( int start ) {
		return valuesBetween( start, size() );
	}

	public List<V> values() {
		return valuesBetween( 0, size() );
	}

	public boolean isCaching( K key ) {
		return cached.containsKey( key );
	}

	public void remove( K k ) {
		if ( isCaching( k ) )
			realSize--;
		cached.remove( k );
	}

	public void remove( V v ) {
		remove( keyMapper.apply( v ) );
	}

	public void clean() {
		AtomicInteger count = new AtomicInteger( 0 );
		cached.entrySet().removeIf( e -> {
			boolean isExpired = e.getValue().isExpired( maxStorageTime );
			if ( isExpired ) count.incrementAndGet();
			return isExpired;
		} );
		realSize -= count.get();
	}

	@Override
	public boolean equals( Object o ) {
		if ( this == o ) return true;
		if ( !( o instanceof Storage<?, ?> storage ) ) return false;
		return Objects.equals( cached, storage.cached ) &&
				Objects.equals( keyMapper, storage.keyMapper );
	}

	@Override
	public int hashCode() {
		return Objects.hash( cached, keyMapper );
	}
}

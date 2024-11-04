package se.pj.tbike.caching;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;

public class CacheManager<K extends Comparable<K>, V extends Cacheable> {

	private final Map<K, CachedData<V>> table;
	private final Function<V, K> keyMapper;
	private final CacheController defaultController;
	private final AtomicInteger size;

	public CacheManager(CacheController defaultController,
	                    Function<V, K> keyMapper) {
		if ( keyMapper == null ) {
			throw new IllegalArgumentException();
		}
		this.keyMapper = keyMapper;
		this.table = new TreeMap<>();
		this.defaultController = defaultController;
		this.size = new AtomicInteger( 0 );
	}

	public CacheManager(Function<V, K> keyMapper) {
		this( new CacheController(), keyMapper );
	}

	public CachedData<V> cache(final K key, final V value,
	                           final CacheController controller) {
		if ( key == null ) {
			throw new IllegalArgumentException();
		}
		final CachedData<V> o = table.get( key );
		if ( o != null ) {
			if ( o.isEmpty() ) {
				size.getAndIncrement();
			}
			return o.replace( value );
		}
		final CacheController cc = controller != null
				? controller
				: defaultController;
		final CachedData<V> no = new CachedData<>( value, cc, () -> {
			size.decrementAndGet();
			String type = value != null
					? value.getClass().getName()
					: "Unknown type";
			return "Release completed: " + type + " with key: " + key + "." +
					" Current cache size: " + size + ".";
		} );
		table.put( key, no );
		if ( value != null ) {
			size.getAndIncrement();
		}
		return no;
	}

	public CachedData<V> cache(K key, V value) {
		return cache( key, value, defaultController );
	}

	public CachedData<V> cache(V value, CacheController controller) {
		if ( value == null ) {
			throw new IllegalArgumentException();
		}
		K key = keyMapper.apply( value );
		return cache( key, value, controller );
	}

	public CachedData<V> cache(V value) {
		return cache( value, defaultController );
	}

	public CachedData<V> get(K key) {
		CachedData<V> o = table.get( key );
		return o == null ? cache( key, null ) : o;
	}

	public void update(K key, V value) {
		if ( key == null ) {
			throw new IllegalArgumentException();
		}
		CachedData<V> o = table.get( key );
		if ( o == null ) {
			cache( key, value );
		} else {
			if ( o.isEmpty() ) {
				size.getAndIncrement();
			}
			o.replace( value );
		}
	}

	public void update(V v) {
		K key = keyMapper.apply( v );
		update( key, v );
	}

	public void remove(K k) {
		if ( isCaching( k ) ) {
			size.getAndDecrement();
		}
		table.remove( k );
	}

	public void remove(V v) {
		remove( keyMapper.apply( v ) );
	}

	public Stream<V> stream() {
		return table.values()
				.stream()
				.filter( CachedData::isPresent )
				.map( CachedData::get );
	}

	public int size() {
		return size.get();
	}

	public boolean isCaching(K key) {
		CachedData<V> obj = table.get( key );
		return obj != null && obj.isPresent();
	}

	public boolean isEmpty() {
		return table.isEmpty() || size.get() == 0;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof CacheManager<?, ?> that) ) {
			return false;
		}
		return (size.get() == that.size.get()) &&
				Objects.equals( table, that.table ) &&
				Objects.equals( keyMapper, that.keyMapper ) &&
				Objects.equals( defaultController, that.defaultController );
	}

	@Override
	public int hashCode() {
		return Objects.hash( table, keyMapper, size, defaultController );
	}
}

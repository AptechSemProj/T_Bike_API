package se.pj.tbike.caching;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Stream;

public class CacheManager<K extends Comparable<K>, V extends Cacheable> {

	private final Map<K, CachedData<V>> table;
	private final Function<V, K> keyMapper;
	private final CacheController defaultController;
	private int size;

	public CacheManager(CacheController defaultController,
	                    Function<V, K> keyMapper) {
		if ( keyMapper == null ) {
			throw new IllegalArgumentException();
		}
		this.keyMapper = keyMapper;
		this.table = new TreeMap<>();
		this.defaultController = defaultController;
	}

	public CacheManager(Function<V, K> keyMapper) {
		this( new CacheController(), keyMapper );
	}

	public void cache(K key, V value, CacheController controller) {
		if ( key == null ) {
			throw new IllegalArgumentException();
		}
		CachedData<V> cache = table.get( key );
		if ( cache == null ) {
			if ( controller != null ) {
				cache = new CachedData<>( value, controller );
			} else {
				cache = new CachedData<>( value, defaultController );
			}
			table.put( key, cache );
			if ( value != null ) {
				size++;
			}
		} else {
			cache.replace( value );
		}
	}

	public void cache(V value, CacheController controller) {
		if ( value == null ) {
			throw new IllegalArgumentException();
		}
		K key = keyMapper.apply( value );
		cache( key, value, controller );
	}

	public void cache(K key, V value) {
		cache( key, value, defaultController );
	}

	public void cache(V value) {
		cache( value, defaultController );
	}

	public CachedData<V> get(K key) {
		CachedData<V> o = table.get( key );
		if ( o == null ) {
			o = new CachedData<>( null, defaultController );
			table.put( key, o );
		}
		return o;
	}

	public void update(K key, V value) {
		if ( key == null ) {
			throw new IllegalArgumentException();
		}
		CachedData<V> cache = table.get( key );
		if ( cache == null ) {
			table.put(
					key,
					new CachedData<>( value, defaultController )
			);
		} else {
			cache.replace( value );
		}
	}

	public void update(V v) {
		K key = keyMapper.apply( v );
		update( key, v );
	}

	public void remove(K k) {
		if ( isCaching( k ) ) {
			size--;
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
		return size;
	}

	public boolean isCaching(K key) {
		CachedData<V> obj = table.get( key );
		return obj != null && obj.isPresent();
	}

	public boolean isEmpty() {
		return table.isEmpty() || size == 0;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof CacheManager<?, ?> that) ) {
			return false;
		}
		return size == that.size &&
				Objects.equals( table, that.table ) &&
				Objects.equals( keyMapper, that.keyMapper ) &&
				Objects.equals( defaultController, that.defaultController );
	}

	@Override
	public int hashCode() {
		return Objects.hash( table, keyMapper, size, defaultController );
	}
}

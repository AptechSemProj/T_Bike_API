package se.pj.tbike.caching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public final class CacheManager<K extends Comparable<K>> {

    public static final Logger LOGGER = LoggerFactory.getLogger(CacheManager.class);

    private final Map<K, Cache> table;
    private final CacheControl defaultController;
    private final AtomicInteger size;

    public CacheManager(CacheControl defaultController) {
        this.table = new TreeMap<>();
        this.defaultController = defaultController;
        this.size = new AtomicInteger(0);
    }

    private String printCache(Map<String, Object> map) {
        StringBuilder o = new StringBuilder();
        if (map == null) {
            o.append("null");
        } else if (map.isEmpty()) {
            o.append("{}");
        } else {
            o.append("{").append(System.lineSeparator());
            o.append(String.join(
                    "," + System.lineSeparator(),
                    map.entrySet()
                            .stream()
                            .map(e -> e.getKey() + " = " + e.getValue())
                            .toList()
            ).indent(4));
            o.append("}");
        }
        return o.toString();
    }

    private void logUpdate(Map<String, Object> old, Map<String, Object> newCache) {
        String s = "Current cache size: {}."
                + System.lineSeparator()
                + "Updated: {} -> {}.";
        LOGGER.info(s, size, printCache(old), printCache(newCache));
    }

    private void logCached(Cache value) {
        String s = "Current cache size: {}."
                + System.lineSeparator()
                + "Cached: {}.";
        LOGGER.info(s, size, printCache(value.get()));
    }

    private void logRelease(Cache value) {
        String s = "Current cache size: {}."
                + System.lineSeparator()
                + "Released: {}.";
        LOGGER.info(s, size, printCache(value.get()));
    }

    private Cache cache(K key, Map<String, Object> value,
                        CacheControl controller, boolean checked) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        Map<String, Object> old;
        if (!checked) {
            Cache o = table.get(key);
            if (o != null) {
                old = o.get();
                if (o.isNull() && value != null) {
                    size.getAndIncrement();
                }
                o.set(value);
//				logUpdate( old, value );
                return o;
            }
        }
        Cache newCache = new Cache(
                value,
                controller != null ? controller : defaultController,
                cache -> {
                    if (cache.isPresent()) {
                        size.decrementAndGet();
                    }
//					logRelease( cache );
                }
        );
        table.put(key, newCache);
        if (value != null) {
            size.getAndIncrement();
        }
//		logCached( newCache );
        return newCache;
    }

    public Cache cache(K key, Map<String, Object> value, CacheControl controller) {
        return cache(key, value, controller, false);
    }

    public Cache cache(K key, Map<String, Object> value) {
        return cache(key, value, null, false);
    }

    public Cache get(K key) {
        Cache o = table.get(key);
        if (o != null) {
            return o;
        }
        return cache(key, null, null, true);
    }

    public void empty(Cache cache) {
//		Map<String, Object> old = cache.get();
        cache.set(Map.of());
//		logUpdate( old, Map.of() );
    }

    public void set(Cache cache, Map<String, Object> value) {
        if (cache.isNull()) {
            size.getAndIncrement();
        }
        Map<String, Object> old = cache.get();
        cache.set(value);
//		logUpdate( old, value );
    }

    public void set(K key, Map<String, Object> value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        Cache cache = table.get(key);
        if (cache == null) {
            cache(key, value, defaultController, true);
        } else {
            set(cache, value);
        }
    }

    public void remove(K key) {
        Cache cache = table.get(key);
        if (cache != null && cache.isPresent()) {
            size.decrementAndGet();
            cache.set(null);
        }
    }

    public Stream<Cache> stream() {
        return table.values()
                .stream()
                .filter(Cache::isPresent);
    }

    public int size() {
        return size.get();
    }

    public boolean isCaching(K key) {
        Cache o = table.get(key);
        return o != null && o.isPresent();
    }

    public boolean isEmpty() {
        return table.isEmpty() || size.get() == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CacheManager<?> that)) {
            return false;
        }
        return (size.get() == that.size.get()) &&
                Objects.equals(table, that.table) &&
                Objects.equals(defaultController, that.defaultController);
    }

    @Override
    public int hashCode() {
        return Objects.hash(table, size, defaultController);
    }
}

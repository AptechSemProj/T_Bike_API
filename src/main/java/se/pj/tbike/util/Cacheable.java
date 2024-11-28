package se.pj.tbike.util;

import java.util.Map;

public interface Cacheable<T> {

	Map<String, Object> toCacheObject();

	T fromCacheObject(Map<String, Object> cacheObject);

}

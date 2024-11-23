package se.pj.tbike.service;

/**
 * @param <K> type of key.
 * @param <T> type of entity.
 */
public interface RemovalService<T, K> {

	boolean removeByKey(K id);

	boolean remove(T t);

}

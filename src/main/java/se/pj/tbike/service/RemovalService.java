package se.pj.tbike.service;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @param <K> type of key.
 * @param <T> type of entity.
 */
public interface RemovalService<T, K> {

	JpaRepository<T, K> getRepository();

	boolean removeByKey(K id);

	boolean remove(T t);

}

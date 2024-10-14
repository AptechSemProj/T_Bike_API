package se.pj.tbike.service;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @param <K> type of key.
 * @param <T> type of entity.
 */
public interface RemovalService<T, K extends Comparable<K>> {

	JpaRepository<T, K> getRepository();

	boolean remove( K id );

	boolean remove( T t );

	boolean remove( K id, T t );

}

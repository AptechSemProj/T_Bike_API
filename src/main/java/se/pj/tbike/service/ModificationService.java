package se.pj.tbike.service;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @param <T> type of entity
 */
public interface ModificationService<T> {

	JpaRepository<T, ?> getRepository();

	boolean update( T t );

	boolean update( T newVal, T oldVal );

}

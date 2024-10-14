package se.pj.tbike.service;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @param <T> type of entity
 */
public interface CreationService<T> {

	JpaRepository<T, ?> getRepository();

	T create( T t );

}

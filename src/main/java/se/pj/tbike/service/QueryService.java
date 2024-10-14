package se.pj.tbike.service;

import se.pj.tbike.util.result.ResultPage;
import se.pj.tbike.util.result.ResultList;
import se.pj.tbike.util.result.Result;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @param <K> type of key
 * @param <T> type of entity
 */
public interface QueryService<T, K extends Comparable<K>> {

	JpaRepository<T, K> getRepository();

	ResultList<T> findAll();

	ResultList<T> findAll( Sort sort );

	ResultPage<T> findPage( int num, int size );

	Result<T> findByKey( K id );

	boolean exists( T t );

	boolean exists( K id );

}

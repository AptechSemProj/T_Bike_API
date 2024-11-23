package se.pj.tbike.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import se.pj.tbike.util.Output.Array;
import se.pj.tbike.util.Output.Pagination;
import se.pj.tbike.util.Output.Value;

/**
 * @param <K> type of key
 * @param <T> type of entity
 */
public interface QueryService<T, K> {

	Array<T> findAll();

	Array<T> findAll(Sort sort);

	Pagination<T> findPage(int num, int size);

	Value<T> findByKey(K id);

	boolean exists(T t);

	boolean existsByKey(K id);

}

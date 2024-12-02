package se.pj.tbike.common.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * @param <T>  type of entity
 * @param <ID> type of id
 */
public interface QueryService<T, ID> {

    List<T> findAll();

    List<T> findAll(Sort sort);

    Page<T> findPage(Pageable pageable);

    Optional<T> findByKey(ID id);

    boolean exists(T t);

    boolean existsById(ID id);

}

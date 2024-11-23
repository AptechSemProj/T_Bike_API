package se.pj.tbike.service;

import com.ank.japi.annotation.Old;

/**
 * @param <T> type of entity
 */
@Old
public interface ModificationService<T> {

	boolean update(T t);

	boolean update(T newVal, T oldVal);

}

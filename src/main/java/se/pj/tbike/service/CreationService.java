package se.pj.tbike.service;

import com.ank.japi.annotation.Old;

/**
 * @param <T> type of entity
 */
@Old
public interface CreationService<T> {

	T create(T t);

}

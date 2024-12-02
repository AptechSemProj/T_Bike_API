package se.pj.tbike.common.service;

/**
 * @param <T> type of entity
 */
public interface CreationService<T> {

    T create(T t);

}

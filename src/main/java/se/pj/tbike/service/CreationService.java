package se.pj.tbike.service;

/**
 * @param <T> type of entity
 */
public interface CreationService<T> {

    T create(T t);

}

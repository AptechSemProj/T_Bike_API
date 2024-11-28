package se.pj.tbike.service;

/**
 * @param <T> type of entity
 */
public interface ModificationService<T> {

    boolean update(T t);

    boolean update(T newVal, T oldVal);

}

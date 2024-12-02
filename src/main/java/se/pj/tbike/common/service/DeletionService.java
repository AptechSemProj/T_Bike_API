package se.pj.tbike.common.service;

/**
 * @param <T> type of entity.
 * @param <ID> type of id.
 */
public interface DeletionService<T, ID> {

    boolean delete(T t);

    boolean deleteById(ID id);

}

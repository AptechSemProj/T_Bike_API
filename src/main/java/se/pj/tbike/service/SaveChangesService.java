package se.pj.tbike.service;

public interface SaveChangesService<T> {

    enum Action {
        INSERT, UPDATE, DYNAMIC,
    }

    T save(T t, Action action);

    default T save(T t) {
        return save(t, Action.DYNAMIC);
    }

}

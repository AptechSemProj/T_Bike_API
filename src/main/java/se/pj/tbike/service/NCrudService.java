package se.pj.tbike.service;

public interface NCrudService<T, ID>
        extends QueryService<T, ID>,
        RemovalService<T, ID>,
        SaveChangesService<T> {
}

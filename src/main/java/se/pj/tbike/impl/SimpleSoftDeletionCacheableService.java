package se.pj.tbike.impl;

import se.pj.tbike.common.entity.SoftDeletionEntity;
import se.pj.tbike.common.respository.SoftDeletionRepository;
import se.pj.tbike.common.service.CrudService;
import se.pj.tbike.util.Cacheable;

import java.util.function.Supplier;

public abstract class SimpleSoftDeletionCacheableService<
        E extends SoftDeletionEntity<E, ID> & Cacheable<E>,
        ID extends Comparable<ID>,
        R extends SoftDeletionRepository<E, ID>
        > extends SimpleCacheableService<E, ID, R>
        implements CrudService<E, ID> {

    public SimpleSoftDeletionCacheableService(
            R repository,
            Supplier<E> instanceSupplier
    ) {
        super(repository, instanceSupplier);
    }
}

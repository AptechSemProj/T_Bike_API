package se.pj.tbike.core.util;

import se.pj.tbike.core.common.entity.SoftDeletionEntity;
import se.pj.tbike.core.common.respository.SoftDeletionRepository;
import se.pj.tbike.service.CrudService;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class SoftDeletionCacheableService<
		E extends SoftDeletionEntity<E, ID> & Cacheable<E>,
		ID extends Comparable<ID>,
		R extends SoftDeletionRepository<E, ID>
		> extends SimpleCacheableService<E, ID, R>
		implements CrudService<E, ID> {

	public SoftDeletionCacheableService(R repository,
	                                    Supplier<E> instanceSupplier,
	                                    Function<E, ID> keyProvider) {
		super( repository, instanceSupplier, keyProvider );
	}
}

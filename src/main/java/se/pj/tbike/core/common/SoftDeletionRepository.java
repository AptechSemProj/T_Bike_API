package se.pj.tbike.core.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SoftDeletionRepository<E extends SoftDeletionEntity<E>, ID> {

	@Query
	List<E> findByDeletedFalse();

	@Query
	Page<E> findByDeletedFalse(Pageable pageable);

	@Query
	long countByDeletedFalse();

}

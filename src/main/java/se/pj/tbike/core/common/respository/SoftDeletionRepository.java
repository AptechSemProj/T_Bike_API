package se.pj.tbike.core.common.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import se.pj.tbike.core.common.entity.SoftDeletionEntity;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface SoftDeletionRepository<
		E extends SoftDeletionEntity<E, ID>,
		ID extends Comparable<ID>
		> extends JpaRepository<E, ID> {

	@Modifying
	@Transactional
	@Query("UPDATE #{#entityName} e SET e.deleted=true WHERE e.id=:id")
	@Override
	void deleteById(@NonNull ID id);

	@Modifying
	@Transactional
	@Query("UPDATE #{#entityName} e SET e.deleted=true WHERE e=:entity")
	@Override
	void delete(@NonNull E entity);

	@Query("SELECT e FROM #{#entityName} e WHERE e.id=:id AND e.deleted=false")
	@NonNull
	@Override
	Optional<E> findById(@NonNull ID id);

	@Query("""
			select
			case when count(e) > 0 then true
			else false end
			from #{#entityName} e
			where e.id=:id and e.deleted=false
			""")
	@Override
	boolean existsById(@NonNull ID id);

	@Query("SELECT e FROM #{#entityName} e WHERE e.deleted=false")
	@NonNull
	@Override
	List<E> findAll();

	@Query("select e from #{#entityName} e where e.deleted=false")
	@NonNull
	@Override
	List<E> findAll(@NonNull Sort sort);

	@Query("select e from #{#entityName} e where e.deleted=false")
	@NonNull
	@Override
	Page<E> findAll(@NonNull Pageable pageable);

	@Query("select count(e.id) from #{#entityName} e where e.deleted=false")
	@Override
	long count();

}

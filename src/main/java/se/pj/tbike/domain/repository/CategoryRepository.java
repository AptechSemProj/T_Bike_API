package se.pj.tbike.domain.repository;

import org.springframework.stereotype.Repository;
import se.pj.tbike.domain.entity.Category;
import se.pj.tbike.common.respository.SoftDeletionRepository;

@Repository
public interface CategoryRepository
        extends SoftDeletionRepository<Category, Long> {
}

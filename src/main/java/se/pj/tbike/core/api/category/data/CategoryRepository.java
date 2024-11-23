package se.pj.tbike.core.api.category.data;

import org.springframework.stereotype.Repository;
import se.pj.tbike.core.api.category.entity.Category;
import se.pj.tbike.core.common.respository.SoftDeletionRepository;

@Repository
public interface CategoryRepository
        extends SoftDeletionRepository<Category, Long> {
}

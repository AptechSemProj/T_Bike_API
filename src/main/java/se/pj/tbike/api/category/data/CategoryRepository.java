package se.pj.tbike.api.category.data;

import org.springframework.stereotype.Repository;
import se.pj.tbike.api.category.entity.Category;
import se.pj.tbike.common.respository.SoftDeletionRepository;

@Repository
public interface CategoryRepository
        extends SoftDeletionRepository<Category, Long> {
}

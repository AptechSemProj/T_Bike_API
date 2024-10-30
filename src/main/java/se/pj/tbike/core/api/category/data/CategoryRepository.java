package se.pj.tbike.core.api.category.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.pj.tbike.core.api.category.entity.Category;
import se.pj.tbike.core.common.SoftDeletionRepository;

@Repository
public interface CategoryRepository
		extends JpaRepository<Category, Long>,
		SoftDeletionRepository<Category, Long> {
}

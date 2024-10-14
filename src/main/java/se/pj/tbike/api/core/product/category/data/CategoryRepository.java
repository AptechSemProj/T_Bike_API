package se.pj.tbike.api.core.product.category.data;

import org.springframework.data.jpa.repository.JpaRepository;
import se.pj.tbike.api.core.product.Product.Category;

public interface CategoryRepository
		extends JpaRepository<Category, Long> {
}

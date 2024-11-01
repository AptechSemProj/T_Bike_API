package se.pj.tbike.core.api.product.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.pj.tbike.core.api.product.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository
		extends JpaRepository<Product, Long> {

	List<Product> findAllByBrandId(@Param("brandId") Long brandId);

	List<Product> findAllByCategoryId(@Param("categoryId") Long categoryId);

	@Query("""
			SELECT p_1
			FROM Product p_1
			JOIN Attribute AS a_1
			ON p_1.id = a_1.product.id
			WHERE (:name IS NULL OR LOWER(p_1.name) LIKE LOWER(CONCAT('%', :name, '%')))
			AND (:brand IS NULL OR p_1.brand.id = :brand)
			AND (:category IS NULL OR p_1.category.id = :category)
			AND a_1.price
			BETWEEN (
			    CASE WHEN (:minPrice IS NULL)
			        THEN 0
			        ELSE :minPrice
			    END
			)
			AND (
			    CASE WHEN (:maxPrice IS NULL)
			        THEN (SELECT MAX(a_2.price) FROM Attribute a_2)
			        ELSE :maxPrice
			    END
			)""")
	Page<Product> search(@Param("name") String name,
	                     @Param("brand") Long brandId,
	                     @Param("category") Long categoryId,
	                     @Param("minPrice") Long min,
	                     @Param("maxPrice") Long max,
	                     Pageable pageable);

}

package se.pj.tbike.core.api.product.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.pj.tbike.core.api.product.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository
		extends JpaRepository<Product, Long> {

	List<Product> findAllByBrandId(@Param("brandId") Long brandId);

	void deleteAllByBrandId(@Param("brandId") Long brandId);

	void deleteAllByCategoryId(@Param("categoryId") Long categoryId);

}

package se.pj.tbike.api.core.product.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.pj.tbike.api.core.product.Product;

import java.util.List;

@Repository
public interface ProductRepository
		extends JpaRepository<Product, Long> {

	List<Product> findAllByBrandId( long brandId );

	List<Product> findAllByCategoryId( long categoryId );

}

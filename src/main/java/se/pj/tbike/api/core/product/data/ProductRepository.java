package se.pj.tbike.api.core.product.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import se.pj.tbike.api.core.product.Product;

@Repository
public interface ProductRepository
		extends JpaRepository<Product, Long> {

	void deleteAllByBrandId( @Param( "brandId" ) long brandId );

	void deleteAllByCategoryId( @Param( "categoryId" ) long categoryId );

}

package se.pj.tbike.core.api.product.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.pj.tbike.core.api.product.entity.Product;

import java.util.Optional;

@Repository
public interface ProductRepository
        extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {

    Optional<Product> findBySku(@Param("sku") String sku);

}

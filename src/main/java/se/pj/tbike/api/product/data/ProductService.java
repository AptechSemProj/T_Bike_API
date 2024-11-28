package se.pj.tbike.api.product.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import se.pj.tbike.api.product.entity.Product;
import se.pj.tbike.service.CrudService;

import java.util.Optional;

public interface ProductService
        extends CrudService<Product, Long> {

    Page<Product> search(Pageable pageable,
                         String name, Long brand, Long category,
                         Range<Long> priceRange);

    Optional<Product> findBySku(String sku);

}

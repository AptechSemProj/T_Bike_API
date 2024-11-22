package se.pj.tbike.core.api.product.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import se.pj.tbike.core.api.product.entity.Product;
import se.pj.tbike.service.CrudService;
import se.pj.tbike.util.Output.Pagination;

import java.util.Optional;

public interface ProductService
        extends CrudService<Product, Long> {

    Pagination<Product> search(Pageable pageable,
                               String name, Long brand, Long category,
                               Range<Long> priceRange);

    Optional<Product> findBySku(String sku);

}

package se.pj.tbike.core.api.product.data;

import se.pj.tbike.core.api.product.entity.Product;
import se.pj.tbike.service.CrudService;

public interface ProductService
		extends CrudService<Product, Long> {

	void deleteAllByBrandId(Long brandId);

}

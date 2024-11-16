package se.pj.tbike.core.api.product.data;

import se.pj.tbike.core.api.brand.entity.Brand;
import se.pj.tbike.core.api.product.entity.Product;
import se.pj.tbike.service.CrudService;
import se.pj.tbike.util.Output.Pagination;

public interface ProductService
		extends CrudService<Product, Long> {

	void deleteByBrand(Brand brand);

	Pagination<Product> search(int page, int size,
	                           String name, Long maxPrice, Long minPrice,
	                           Long brand, Long category);

	Product findBySku(String sku);

}

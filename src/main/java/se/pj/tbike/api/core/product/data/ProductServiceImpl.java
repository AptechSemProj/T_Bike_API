package se.pj.tbike.api.core.product.data;

import se.pj.tbike.api.common.entity.IdentifiedEntity;
import se.pj.tbike.api.core.product.Product;
import se.pj.tbike.service.StdCrudService;
import se.pj.tbike.util.cache.Storage;

public class ProductServiceImpl
		extends StdCrudService<Product, Long>
		implements ProductService {


	public ProductServiceImpl( ProductRepository repository,
	                           Storage<Long, Product> storage ) {
		super( repository, IdentifiedEntity::getId );
	}
}

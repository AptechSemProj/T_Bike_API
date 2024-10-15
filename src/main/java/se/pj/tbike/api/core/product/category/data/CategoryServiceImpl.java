package se.pj.tbike.api.core.product.category.data;

import se.pj.tbike.api.common.entity.IdentifiedEntity;
import se.pj.tbike.api.core.product.Product.Category;
import se.pj.tbike.service.StdCrudService;
import se.pj.tbike.util.cache.Storage;

public class CategoryServiceImpl
		extends StdCrudService<Category, Long>
		implements CategoryService {

	public CategoryServiceImpl( CategoryRepository repository,
	                            Storage<Long, Category> storage ) {
		super( repository, IdentifiedEntity::getId );
	}
}

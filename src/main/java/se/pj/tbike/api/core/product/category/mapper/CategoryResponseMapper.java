package se.pj.tbike.api.core.product.category.mapper;

import se.pj.tbike.api.core.product.Product.Category;
import se.pj.tbike.api.core.product.category.dto.CategoryResponse;

public class CategoryResponseMapper
		implements CategoryResMapper<CategoryResponse> {

	@Override
	public CategoryResponse map( Category category ) {
		return new CategoryResponse( category.getId(), category.getName(),
				category.getDescription() );
	}
}

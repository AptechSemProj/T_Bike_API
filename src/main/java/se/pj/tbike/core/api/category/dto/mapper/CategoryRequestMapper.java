package se.pj.tbike.core.api.category.dto.mapper;

import se.pj.tbike.core.api.category.entity.Category;
import se.pj.tbike.io.RequestMapper;

import se.pj.tbike.core.api.category.dto.CategoryRequest;

public class CategoryRequestMapper
		implements RequestMapper<Category, CategoryRequest> {

	@Override
	public Category map(CategoryRequest req ) {
		Category category = new Category();
		category.setName( req.getName() );
		category.setImageUrl( req.getImageUrl() );
		category.setDescription( req.getDescription() );
		return category;
	}
}

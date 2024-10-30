package se.pj.tbike.core.api.category.dto.mapper;

import se.pj.tbike.core.api.category.dto.CategoryResponse.Builder;
import se.pj.tbike.core.api.category.entity.Category;
import se.pj.tbike.io.ResponseMapper;
import se.pj.tbike.core.api.category.dto.CategoryResponse;

public class CategoryResponseMapper
		implements ResponseMapper<Category, CategoryResponse> {

	@Override
	public CategoryResponse map(Category category) {
		Builder builder = CategoryResponse.builder();
		builder.id( category.getId() );
		builder.name( category.getName() );
		builder.imageUrl( category.getImageUrl() );
		builder.description( category.getDescription() );
		return builder.build();
	}
}

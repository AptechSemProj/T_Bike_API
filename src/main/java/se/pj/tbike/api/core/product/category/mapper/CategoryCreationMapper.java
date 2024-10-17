package se.pj.tbike.api.core.product.category.mapper;

import se.pj.tbike.api.core.product.Product.Category;
import se.pj.tbike.api.core.product.category.dto.CategoryCreation;

public class CategoryCreationMapper
		implements CategoryReqMapper<CategoryCreation> {

	@Override
	public Category map( CategoryCreation req ) {
		return new Category( req.getName(), req.getDescription() );
	}
}

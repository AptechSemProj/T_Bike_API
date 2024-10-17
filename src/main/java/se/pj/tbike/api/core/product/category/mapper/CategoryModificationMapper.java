package se.pj.tbike.api.core.product.category.mapper;

import lombok.RequiredArgsConstructor;

import se.pj.tbike.api.core.product.Product.Category;
import se.pj.tbike.api.core.product.category.data.CategoryRepository;
import se.pj.tbike.api.core.product.category.dto.CategoryModification;

import java.util.Optional;

@RequiredArgsConstructor
public class CategoryModificationMapper
		implements CategoryReqMapper<CategoryModification> {

	private final CategoryRepository repository;

	@Override
	public Category map( CategoryModification req ) {
		Optional<Category> o = repository.findById( req.getId() );
		if ( o.isPresent() ) {
			Category c = o.get();
			c.setName( req.getName() );
			c.setDescription( req.getDescription() );
			return c;
		}
		return null;
	}
}

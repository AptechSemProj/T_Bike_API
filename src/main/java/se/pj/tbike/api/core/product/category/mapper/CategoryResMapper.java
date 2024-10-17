package se.pj.tbike.api.core.product.category.mapper;

import se.pj.tbike.api.core.product.Product.Category;
import se.pj.tbike.api.io.ResponseMapper;
import se.pj.tbike.api.io.ResponseType;

public interface CategoryResMapper<T extends ResponseType>
		extends ResponseMapper<Category, T> {
}

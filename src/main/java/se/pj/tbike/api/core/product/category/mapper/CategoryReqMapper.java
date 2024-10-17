package se.pj.tbike.api.core.product.category.mapper;

import se.pj.tbike.api.core.product.Product.Category;
import se.pj.tbike.api.io.RequestMapper;
import se.pj.tbike.api.io.RequestType;

public interface CategoryReqMapper<T extends RequestType>
		extends RequestMapper<Category, T> {
}

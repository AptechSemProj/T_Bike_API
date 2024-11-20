package se.pj.tbike.core.api.product.dto.mapper;

import java.util.List;

import lombok.RequiredArgsConstructor;

import se.pj.tbike.core.api.attribute.entity.Attribute;
import se.pj.tbike.core.api.brand.dto.BrandMapper;

import se.pj.tbike.core.api.product.entity.Product;
import se.pj.tbike.core.api.product.dto.ProductResponse;

import se.pj.tbike.core.api.category.dto.mapper.CategoryResponseMapper;

import se.pj.tbike.io.ResponseMapper;

@RequiredArgsConstructor
public class ProductResponseMapper
		implements ResponseMapper<Product, ProductResponse> {

	private final BrandMapper brandMapper;
	private final CategoryResponseMapper categoryMapper;

	@Override
	public ProductResponse map(Product product) {
		var brand = brandMapper.map( product.getBrand() );

		var category = categoryMapper.map( product.getCategory() );

		List<Attribute> list = product.getAttributes();
		Attribute attr = list.stream()
				.filter( Attribute::isRepresent )
				.findFirst()
				.orElseThrow();

		return new ProductResponse(
				product.getId(),
				product.getSku(),
				product.getName(),
				brand,
				category,
				attr.getImageUrl(),
				attr.getPrice()
		);
	}
}

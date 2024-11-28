package se.pj.tbike.api.product.dto.mapper;

import java.util.List;

import lombok.RequiredArgsConstructor;

import se.pj.tbike.api.attribute.entity.Attribute;
import se.pj.tbike.api.brand.dto.BrandMapper;

import se.pj.tbike.api.category.dto.CategoryMapper;
import se.pj.tbike.api.product.entity.Product;
import se.pj.tbike.api.product.dto.ProductResponse;

@RequiredArgsConstructor
public class ProductResponseMapper {

	private final BrandMapper brandMapper;
	private final CategoryMapper categoryMapper;

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

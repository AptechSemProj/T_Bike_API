package se.pj.tbike.http.model.product;

import java.util.List;

import lombok.RequiredArgsConstructor;

import se.pj.tbike.domain.entity.Attribute;
import se.pj.tbike.http.model.brand.BrandMapper;

import se.pj.tbike.http.model.category.CategoryMapper;
import se.pj.tbike.domain.entity.Product;

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

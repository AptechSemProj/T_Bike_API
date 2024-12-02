package se.pj.tbike.http.controller.product.dto.mapper;

import org.modelmapper.ModelMapper;
import se.pj.tbike.http.controller.attribute.AttributeMapper;
import se.pj.tbike.http.model.brand.BrandMapper;
import se.pj.tbike.http.model.brand.BrandResponse;
import se.pj.tbike.http.model.category.CategoryMapper;
import se.pj.tbike.domain.entity.Product;
import se.pj.tbike.http.controller.product.dto.ProductDetail;
import se.pj.tbike.http.controller.product.dto.ProductSpecifications;
import se.pj.tbike.http.controller.attribute.AttributeResponse;
import se.pj.tbike.http.model.category.CategoryResponse;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailMapper {

	private final ModelMapper specificationsMapper;
	private final AttributeMapper attributeMapper;
	private final BrandMapper brandMapper;
	private final CategoryMapper categoryMapper;

	public ProductDetailMapper(
			BrandMapper brandMapper,
			CategoryMapper categoryMapper,
			AttributeMapper attributeMapper) {
		this.specificationsMapper = new ModelMapper();
		this.brandMapper = brandMapper;
		this.categoryMapper = categoryMapper;
		this.attributeMapper = attributeMapper;
	}

	public ProductDetail map(Product product) {
		BrandResponse brand = brandMapper.map( product.getBrand() );
		CategoryResponse category =
				categoryMapper.map( product.getCategory() );

		ProductSpecifications specifications =
				specificationsMapper.map(
						product,
						ProductSpecifications.class
				);

		List<AttributeResponse> attrs = new ArrayList<>();

		product.getAttributes()
				.parallelStream()
				.map( attributeMapper::map )
				.forEach( attrs::add );

		return new ProductDetail(
				product.getId(),
				product.getSku(),
				product.getName(),
				brand,
				category,
				attrs,
				specifications
		);
	}
}

package se.pj.tbike.core.api.product.dto.mapper;

import org.modelmapper.ModelMapper;
import se.pj.tbike.core.api.attribute.dto.AttributeMapper;
import se.pj.tbike.core.api.brand.dto.BrandMapper;
import se.pj.tbike.core.api.brand.dto.BrandResponse;
import se.pj.tbike.core.api.category.dto.CategoryMapper;
import se.pj.tbike.core.api.product.entity.Product;
import se.pj.tbike.core.api.product.dto.ProductDetail;
import se.pj.tbike.core.api.product.dto.ProductSpecifications;
import se.pj.tbike.core.api.attribute.dto.AttributeResponse;
import se.pj.tbike.core.api.category.dto.CategoryResponse;
import se.pj.tbike.io.ResponseMapper;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailMapper
		implements ResponseMapper<Product, ProductDetail> {

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

	@Override
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

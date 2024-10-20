package se.pj.tbike.api.core.product.mapper;

import org.modelmapper.ModelMapper;
import se.pj.tbike.api.core.brand.dto.BrandResponse;
import se.pj.tbike.api.core.brand.mapper.BrandResMapper;
import se.pj.tbike.api.core.product.Product;
import se.pj.tbike.api.core.product.Product.Attribute;
import se.pj.tbike.api.core.product.category.dto.CategoryResponse;
import se.pj.tbike.api.core.product.category.mapper.CategoryResMapper;
import se.pj.tbike.api.core.product.dto.AttributeResponse;
import se.pj.tbike.api.core.product.dto.ProductDetail;
import se.pj.tbike.api.core.product.dto.ProductSpecifications;
import se.pj.tbike.api.io.ResponseMapper;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailMapper
		implements ResponseMapper<Product, ProductDetail> {

	private final ModelMapper specificationsMapper;
	private final ResponseMapper<Attribute, AttributeResponse> attributeResMapper;
	private final BrandResMapper<BrandResponse> brandMapper;
	private final CategoryResMapper<CategoryResponse> categoryResMapper;

	public ProductDetailMapper(
			BrandResMapper<BrandResponse> brandMapper,
			CategoryResMapper<CategoryResponse> categoryResMapper,
			ResponseMapper<Attribute, AttributeResponse> attributeResMapper ) {
		specificationsMapper = new ModelMapper();
		this.brandMapper = brandMapper;
		this.categoryResMapper = categoryResMapper;
		this.attributeResMapper = attributeResMapper;
	}

	@Override
	public ProductDetail map( Product product ) {
		BrandResponse brand = brandMapper.map( product.getBrand() );
		CategoryResponse category =
				categoryResMapper.map( product.getCategory() );

		ProductSpecifications specifications =
				specificationsMapper.map( product,
						ProductSpecifications.class );
		List<AttributeResponse> attrs = new ArrayList<>();

		product.getAttributes()
				.forEach( a -> attrs.add( attributeResMapper.map( a ) ) );

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

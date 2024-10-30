package se.pj.tbike.core.api.product.dto.mapper;

import org.modelmapper.ModelMapper;
import se.pj.tbike.core.api.brand.dto.BrandResponse;
import se.pj.tbike.core.api.brand.dto.mapper.BrandResponseMapper;
import se.pj.tbike.core.api.product.entity.Product;
import se.pj.tbike.core.api.product.dto.ProductDetail;
import se.pj.tbike.core.api.product.dto.ProductSpecifications;
import se.pj.tbike.core.api.attribute.dto.mapper.AttributeResponseMapper;
import se.pj.tbike.core.api.attribute.dto.AttributeResponse;
import se.pj.tbike.core.api.category.dto.CategoryResponse;
import se.pj.tbike.io.ResponseMapper;
import se.pj.tbike.core.api.category.dto.mapper.CategoryResponseMapper;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailMapper
		implements ResponseMapper<Product, ProductDetail> {

	private final ModelMapper specificationsMapper;
	private final AttributeResponseMapper attributeResponseMapper;
	private final BrandResponseMapper brandResponseMapper;
	private final CategoryResponseMapper categoryResponseMapper;

	public ProductDetailMapper(
			BrandResponseMapper brandResponseMapper,
			CategoryResponseMapper categoryResponseMapper,
			AttributeResponseMapper attributeResponseMapper) {
		this.specificationsMapper = new ModelMapper();
		this.brandResponseMapper = brandResponseMapper;
		this.categoryResponseMapper = categoryResponseMapper;
		this.attributeResponseMapper = attributeResponseMapper;
	}

	@Override
	public ProductDetail map(Product product) {
		BrandResponse brand = brandResponseMapper.map( product.getBrand() );
		CategoryResponse category =
				categoryResponseMapper.map( product.getCategory() );

		ProductSpecifications specifications =
				specificationsMapper.map(
						product,
						ProductSpecifications.class
				);

		List<AttributeResponse> attrs = new ArrayList<>();

		product.getAttributes()
				.parallelStream()
				.map( attributeResponseMapper::map )
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

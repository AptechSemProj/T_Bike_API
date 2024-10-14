package se.pj.tbike.api.core.product.mapper;

import java.util.List;

import lombok.RequiredArgsConstructor;

import se.pj.tbike.api.core.brand.Brand;
import se.pj.tbike.api.core.brand.dto.BrandResponse;

import se.pj.tbike.api.core.product.Product;
import se.pj.tbike.api.core.product.Product.Attribute;
import se.pj.tbike.api.core.product.Product.Category;
import se.pj.tbike.api.core.product.category.dto.CategoryResponse;
import se.pj.tbike.api.core.product.dto.ProductResponse;

import se.pj.tbike.api.io.ResponseMapper;

@RequiredArgsConstructor
public class ProductResponseMapper
		implements ResponseMapper<Product, ProductResponse> {

	private final ResponseMapper<Brand, BrandResponse> brandMapper;
	private final ResponseMapper<Category, CategoryResponse> categoryMapper;

	@Override
	public ProductResponse map( Product product ) {
		List<Attribute> list = product.getAttributes();
		Attribute attr = list.stream()
				.filter( Attribute::isRepresent )
				.findFirst().orElseThrow();
		return new ProductResponse(
				product.getId(), product.getSku(), product.getName(),
				brandMapper.map( product.getBrand() ),
				categoryMapper.map( product.getCategory() ),
				attr.getImageUrl(), attr.getPrice() );
	}
}

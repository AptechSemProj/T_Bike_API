package se.pj.tbike.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import se.pj.tbike.api.core.brand.Brand;
import se.pj.tbike.api.core.brand.data.BrandRepository;
import se.pj.tbike.api.core.brand.dto.BrandCreation;
import se.pj.tbike.api.core.brand.dto.BrandModification;
import se.pj.tbike.api.core.brand.dto.BrandResponse;
import se.pj.tbike.api.core.brand.mapper.BrandCreationMapper;
import se.pj.tbike.api.core.brand.mapper.BrandModificationMapper;
import se.pj.tbike.api.core.brand.mapper.BrandReqMapper;
import se.pj.tbike.api.core.brand.mapper.BrandResMapper;
import se.pj.tbike.api.core.brand.mapper.BrandResponseMapper;

import se.pj.tbike.api.core.product.Product;
import se.pj.tbike.api.core.product.Product.Attribute;
import se.pj.tbike.api.core.product.Product.Category;
import se.pj.tbike.api.core.product.category.data.CategoryRepository;
import se.pj.tbike.api.core.product.category.dto.CategoryCreation;
import se.pj.tbike.api.core.product.category.dto.CategoryModification;
import se.pj.tbike.api.core.product.category.dto.CategoryResponse;
import se.pj.tbike.api.core.product.category.mapper.CategoryCreationMapper;
import se.pj.tbike.api.core.product.category.mapper.CategoryModificationMapper;
import se.pj.tbike.api.core.product.category.mapper.CategoryReqMapper;
import se.pj.tbike.api.core.product.category.mapper.CategoryResMapper;
import se.pj.tbike.api.core.product.category.mapper.CategoryResponseMapper;
import se.pj.tbike.api.core.product.dto.AttributeResponse;
import se.pj.tbike.api.core.product.dto.ProductDetail;
import se.pj.tbike.api.core.product.dto.ProductResponse;
import se.pj.tbike.api.core.product.mapper.AttributeResponseMapper;
import se.pj.tbike.api.core.product.mapper.ProductDetailMapper;
import se.pj.tbike.api.core.product.mapper.ProductResponseMapper;

import se.pj.tbike.api.io.ResponseMapper;

@Configuration
public class MappersConfig {

	//********************* Brand ***********************//

	//***************** Request *******************//

	@Bean
	public BrandReqMapper<BrandCreation> brandCreationMapper() {
		return new BrandCreationMapper();
	}

	@Bean
	public BrandReqMapper<BrandModification> brandModificationMapper(
			BrandRepository repository ) {
		return new BrandModificationMapper( repository );
	}

	//***************** Response *******************//

	@Bean
	public BrandResMapper<BrandResponse> brandResponseMapper() {
		return new BrandResponseMapper();
	}

	//*********************** Category *********************//

	//***************** Request *******************//

	@Bean
	public CategoryReqMapper<CategoryCreation> categoryCreationMapper() {
		return new CategoryCreationMapper();
	}

	@Bean
	public CategoryReqMapper<CategoryModification> categoryModificationMapper(
			CategoryRepository repository ) {
		return new CategoryModificationMapper( repository );
	}

	//***************** Response *******************//

	@Bean
	public CategoryResMapper<CategoryResponse> categoryResponseMapper() {
		return new CategoryResponseMapper();
	}

	//********************** Product ************************//

	//***************** Request *******************//

	//***************** Response *******************//

	@Bean
	public ResponseMapper<Product, ProductResponse> productResponseMapper(
			ResponseMapper<Brand, BrandResponse> brandMapper,
			ResponseMapper<Category, CategoryResponse> categoryMapper ) {
		return new ProductResponseMapper( brandMapper, categoryMapper );
	}

	@Bean
	public ResponseMapper<Product, ProductDetail> detailResponseMapper(
			BrandResMapper<BrandResponse> brandMapper,
			CategoryResMapper<CategoryResponse> categoryResMapper,
			ResponseMapper<Attribute, AttributeResponse> attributeResMapper
	) {
		return new ProductDetailMapper( brandMapper, categoryResMapper, attributeResMapper );
	}

	@Bean
	public ResponseMapper<Attribute, AttributeResponse> attributeMapper() {
		return new AttributeResponseMapper();
	}

}

package se.pj.tbike.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import se.pj.tbike.api.core.brand.Brand;
import se.pj.tbike.api.core.brand.dto.BrandCreation;
import se.pj.tbike.api.core.brand.dto.BrandResponse;
import se.pj.tbike.api.core.brand.mapper.BrandCreationMapper;
import se.pj.tbike.api.core.brand.mapper.BrandResponseMapper;

import se.pj.tbike.api.core.product.Product;
import se.pj.tbike.api.core.product.Product.Category;
import se.pj.tbike.api.core.product.category.dto.CategoryResponse;
import se.pj.tbike.api.core.product.category.mapper.CategoryResponseMapper;
import se.pj.tbike.api.core.product.dto.ProductResponse;
import se.pj.tbike.api.core.product.mapper.ProductResponseMapper;

import se.pj.tbike.api.io.RequestMapper;
import se.pj.tbike.api.io.ResponseMapper;

@Configuration
public class MappersConfig {

	@Bean
	public ResponseMapper<Brand, BrandResponse> brandResponseMapper() {
		return new BrandResponseMapper();
	}

	@Bean
	public RequestMapper<Brand, BrandCreation> brandCreationMapper() {
		return new BrandCreationMapper();
	}

	@Bean
	public ResponseMapper<Category, CategoryResponse> categoryResponseMapper() {
		return new CategoryResponseMapper();
	}

	@Bean
	public ResponseMapper<Product, ProductResponse> productResponseMapper(
			ResponseMapper<Brand, BrandResponse> brandMapper,
			ResponseMapper<Category, CategoryResponse> categoryMapper ) {
		return new ProductResponseMapper( brandMapper, categoryMapper );
	}

}

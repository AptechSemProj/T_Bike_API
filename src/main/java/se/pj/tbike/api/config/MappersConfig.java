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
import se.pj.tbike.api.core.product.Product.Category;
import se.pj.tbike.api.core.product.category.dto.CategoryResponse;
import se.pj.tbike.api.core.product.category.mapper.CategoryResponseMapper;
import se.pj.tbike.api.core.product.dto.ProductResponse;
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

	//***************** Response *******************//

	@Bean
	public ResponseMapper<Category, CategoryResponse> categoryResponseMapper() {
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

}

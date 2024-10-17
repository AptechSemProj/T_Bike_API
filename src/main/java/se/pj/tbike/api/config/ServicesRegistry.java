package se.pj.tbike.api.config;

import java.time.Duration;

import jakarta.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

import se.pj.tbike.api.common.entity.IdentifiedEntity;

import se.pj.tbike.api.core.brand.data.BrandRepository;
import se.pj.tbike.api.core.brand.data.BrandService;
import se.pj.tbike.api.core.brand.data.BrandServiceImpl;

import se.pj.tbike.api.core.product.Product;
import se.pj.tbike.api.core.product.category.data.CategoryRepository;
import se.pj.tbike.api.core.product.category.data.CategoryService;
import se.pj.tbike.api.core.product.category.data.CategoryServiceImpl;
import se.pj.tbike.api.core.product.data.ProductRepository;
import se.pj.tbike.api.core.product.data.ProductService;
import se.pj.tbike.api.core.product.data.ProductServiceImpl;

import se.pj.tbike.util.cache.Storage;
import se.pj.tbike.util.cache.StorageManager;

@Configuration
@RequiredArgsConstructor
public class ServicesRegistry {

	public static final String PRODUCTS_STORAGE_KEY = "products";

	private final StorageManager manager;

	@PostConstruct
	public void setup() {

		// products
		manager.register( PRODUCTS_STORAGE_KEY, new Storage<Long, Product>(
				IdentifiedEntity::getId, Duration.ofMinutes( 5 )
		) );

	}

	@Bean
	public BrandService brandService( BrandRepository repository,
	                                  ProductRepository productRepository ) {
		return new BrandServiceImpl( repository, manager, productRepository );
	}

	@Bean
	public CategoryService categoryService( CategoryRepository repository,
	                                        ProductRepository productRepository ) {
		return new CategoryServiceImpl( repository, manager, productRepository );
	}

	@Bean
	public ProductService productService( ProductRepository repository ) {
		Storage<Long, Product> s = manager.use( PRODUCTS_STORAGE_KEY );
		return new ProductServiceImpl( repository, s );
	}


}

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
import se.pj.tbike.api.core.product.Product.Category;
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
	public static final String CATEGORIES_STORAGE_KEY = "categories";

	private final StorageManager manager;

	@PostConstruct
	public void setup() {
		// categories
		manager.register( CATEGORIES_STORAGE_KEY, new Storage<Long, Category>(
				IdentifiedEntity::getId, Duration.ofMinutes( 1 )
		) );
		// products
		manager.register( PRODUCTS_STORAGE_KEY, new Storage<Long, Product>(
				IdentifiedEntity::getId, Duration.ofMinutes( 5 )
		) );

	}

	@Bean
	public BrandService brandService( BrandRepository repository ) {
		return new BrandServiceImpl( repository, manager );
	}

	@Bean
	public CategoryService categoryService( CategoryRepository repository ) {
		Storage<Long, Category> s = manager.use( CATEGORIES_STORAGE_KEY );
		return new CategoryServiceImpl( repository, s );
	}

	@Bean
	public ProductService productService( ProductRepository repository ) {
		Storage<Long, Product> s = manager.use( PRODUCTS_STORAGE_KEY );
		return new ProductServiceImpl( repository, s );
	}


}

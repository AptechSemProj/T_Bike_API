package se.pj.tbike.core.api.product.data;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import se.pj.tbike.core.api.product.entity.Product;
import se.pj.tbike.core.api.attribute.data.AttributeRepository;
import se.pj.tbike.core.util.SimpleCacheableService;
import se.pj.tbike.caching.CacheController;
import se.pj.tbike.caching.CacheManager;

import java.util.List;

import java.time.Duration;

public class ProductServiceImpl
		extends SimpleCacheableService<Product, Long>
		implements ProductService {

	private static final int POOL_SIZE = 10;
	private static final Duration MAX_LIFE_TIME = Duration.ofMinutes( 5 );

	private final ProductRepository repository;
	private final AttributeRepository attributeRepository;


	public ProductServiceImpl(
			ProductRepository repository,
			AttributeRepository attributeRepository) {
		super( repository, Product::getId );
		this.repository = repository;
		this.attributeRepository = attributeRepository;
	}

	@Override
	protected CacheManager<Long, Product> getCacheManager() {
		var scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize( POOL_SIZE );
		scheduler.initialize();
		var controller = new CacheController( scheduler, MAX_LIFE_TIME );
		return new CacheManager<>( controller, Product::getId );
	}

	@Override
	public void deleteAllByBrandId(Long brandId) {
		List<Product> products = repository.findAllByBrandId( brandId );
		products.forEach( p -> {
			attributeRepository.deleteAllByProductId( p.getId() );

		} );
//		attributeRepository.deleteAllByProductId(  );
	}
}

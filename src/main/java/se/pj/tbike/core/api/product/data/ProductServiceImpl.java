package se.pj.tbike.core.api.product.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import se.pj.tbike.core.api.product.entity.Product;
import se.pj.tbike.core.api.attribute.data.AttributeRepository;
import se.pj.tbike.core.util.SimpleCacheableService;
import se.pj.tbike.caching.CacheController;
import se.pj.tbike.caching.CacheManager;
import se.pj.tbike.util.Output.Pagination;

import java.time.Duration;
import java.util.List;

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
	}

	@Override
	public Pagination<Product> search(int page, int size,
	                                  String name, Long[] prices,
	                                  Long brand, Long category) {
		Long min = null, max = null;
		if ( prices != null ) {
			if ( prices.length > 0 ) {
				min = prices[0];
			}
			if ( prices.length > 1 ) {
				max = prices[1];
			}
		}
		PageRequest request = PageRequest.of( page, size );
		Page<Product> paged =
				repository.search( name, brand, category, min, max, request );
		return Pagination.of( paged.toSet(),
				paged.getNumber(), paged.getSize(),
				paged.getTotalElements(), paged.getTotalPages() );
	}
}

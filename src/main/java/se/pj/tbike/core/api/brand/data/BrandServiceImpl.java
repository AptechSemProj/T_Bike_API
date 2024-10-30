package se.pj.tbike.core.api.brand.data;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import se.pj.tbike.core.api.brand.entity.Brand;
import se.pj.tbike.core.util.SoftDeletionCacheableService;
import se.pj.tbike.caching.CacheController;
import se.pj.tbike.caching.CacheManager;

import java.time.Duration;

public class BrandServiceImpl
		extends SoftDeletionCacheableService<Brand, Long, BrandRepository>
		implements BrandService {

	private static final int POOL_SIZE = 10;
	private static final Duration MAX_LIFE_TIME = Duration.ofSeconds( 60 );

	public BrandServiceImpl(BrandRepository repository) {
		super( repository, Brand::getId );
	}

	@Override
	protected CacheManager<Long, Brand> getCacheManager() {

		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize( POOL_SIZE );
		scheduler.initialize();

		CacheController controller =
				new CacheController( scheduler, MAX_LIFE_TIME );

		return new CacheManager<>( controller, Brand::getId );
	}
}

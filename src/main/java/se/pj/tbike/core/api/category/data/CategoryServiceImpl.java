package se.pj.tbike.core.api.category.data;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import se.pj.tbike.caching.CacheController;
import se.pj.tbike.caching.CacheManager;
import se.pj.tbike.core.api.category.entity.Category;
import se.pj.tbike.core.util.SoftDeletionCacheableService;

import java.time.Duration;

public class CategoryServiceImpl
		extends SoftDeletionCacheableService<Category, Long, CategoryRepository>
		implements CategoryService {

	private static final int POOL_SIZE = 10;
	private static final Duration MAX_LIFE_TIME = Duration.ofSeconds( 60 );

	public CategoryServiceImpl(CategoryRepository repository) {
		super( repository, Category::getId );
	}

	@Override
	protected CacheManager<Long, Category> getCacheManager() {

		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize( POOL_SIZE );
		scheduler.initialize();

		CacheController controller =
				new CacheController( scheduler, MAX_LIFE_TIME );

		return new CacheManager<>( controller, Category::getId );
	}
}

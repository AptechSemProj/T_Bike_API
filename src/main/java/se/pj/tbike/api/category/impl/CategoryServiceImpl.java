package se.pj.tbike.api.category.impl;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import se.pj.tbike.caching.CacheControl;
import se.pj.tbike.caching.CacheManager;
import se.pj.tbike.api.category.data.CategoryRepository;
import se.pj.tbike.api.category.data.CategoryService;
import se.pj.tbike.api.category.entity.Category;
import se.pj.tbike.impl.SimpleSoftDeletionCacheableService;

import java.time.Duration;

public class CategoryServiceImpl
        extends SimpleSoftDeletionCacheableService<Category, Long, CategoryRepository>
        implements CategoryService {

    private static final int      POOL_SIZE     = 10;
    private static final Duration MAX_LIFE_TIME = Duration.ofMinutes( 5 );

    public CategoryServiceImpl(CategoryRepository repository) {
        super( repository, Category::new);
    }

    @Override
    protected CacheManager<Long> createCacheManager() {

        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize( POOL_SIZE );
        scheduler.initialize();

        CacheControl controller = new CacheControl( scheduler, MAX_LIFE_TIME );
        return new CacheManager<>( controller );
    }
}

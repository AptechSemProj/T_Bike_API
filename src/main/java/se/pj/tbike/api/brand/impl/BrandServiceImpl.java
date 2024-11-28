package se.pj.tbike.api.brand.impl;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import se.pj.tbike.api.brand.data.BrandRepository;
import se.pj.tbike.api.brand.data.BrandService;
import se.pj.tbike.api.brand.entity.Brand;
import se.pj.tbike.impl.SimpleSoftDeletionCacheableService;
import se.pj.tbike.caching.CacheControl;
import se.pj.tbike.caching.CacheManager;

import java.time.Duration;

public class BrandServiceImpl
        extends SimpleSoftDeletionCacheableService<Brand, Long, BrandRepository>
        implements BrandService {

    private static final int POOL_SIZE = 10;
    private static final Duration MAX_LIFE_TIME = Duration.ofMinutes(5);

    public BrandServiceImpl(BrandRepository repository) {
        super(repository, Brand::new);
    }

    @Override
    protected CacheManager<Long> createCacheManager() {

        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(POOL_SIZE);
        scheduler.initialize();

        CacheControl controller = new CacheControl(scheduler, MAX_LIFE_TIME);
        return new CacheManager<>(controller);
    }
}

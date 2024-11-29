package se.pj.tbike.api.brand.data;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import se.pj.tbike.api.brand.entity.Brand;
import se.pj.tbike.caching.CacheControl;
import se.pj.tbike.caching.CacheManager;
import se.pj.tbike.impl.SimpleSoftDeletionCacheableService;
import se.pj.tbike.service.CrudService;

import java.time.Duration;

@Service
public class BrandService
        extends SimpleSoftDeletionCacheableService<Brand, Long, BrandRepository>
        implements CrudService<Brand, Long> {

    private static final int POOL_SIZE = 10;
    private static final Duration MAX_LIFE_TIME = Duration.ofMinutes(5);

    public BrandService(BrandRepository repository) {
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

package se.pj.tbike.core.api.attribute.data;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import se.pj.tbike.caching.CacheControl;
import se.pj.tbike.caching.CacheManager;
import se.pj.tbike.core.api.attribute.entity.Attribute;
import se.pj.tbike.core.util.SimpleCacheableService;

import java.time.Duration;

public class AttributeServiceImpl
		extends SimpleCacheableService<Attribute, Long, AttributeRepository>
		implements AttributeService {

	private static final int POOL_SIZE = 10;
	private static final Duration MAX_LIFE_TIME = Duration.ofMinutes( 1 );

	public AttributeServiceImpl(AttributeRepository repository) {
		super( repository, Attribute::new, Attribute::getId );
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

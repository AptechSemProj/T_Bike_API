package se.pj.tbike.core.api.attribute.data;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import se.pj.tbike.caching.CacheController;
import se.pj.tbike.caching.CacheManager;
import se.pj.tbike.core.api.attribute.entity.Attribute;
import se.pj.tbike.core.util.SimpleCacheableService;

import java.time.Duration;

public class AttributeServiceImpl
		extends SimpleCacheableService<Attribute, Long>
		implements AttributeService {

	private static final int POOL_SIZE;
	private static final Duration MAX_LIFE_TIME;

	static {
		POOL_SIZE = 10;
		MAX_LIFE_TIME = Duration.ofMinutes( 1 );
	}

	public AttributeServiceImpl(
			AttributeRepository repository) {
		super( repository, Attribute::getId );
	}

	@Override
	protected CacheManager<Long, Attribute> getCacheManager() {
		var scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize( POOL_SIZE );
		scheduler.initialize();
		var controller = new CacheController( scheduler, MAX_LIFE_TIME );
		return new CacheManager<>( controller, Attribute::getId );
	}
}

package se.pj.tbike.core.api.user.data;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import se.pj.tbike.caching.Cache;
import se.pj.tbike.caching.CacheControl;
import se.pj.tbike.caching.CacheManager;
import se.pj.tbike.core.api.user.entity.User;
import se.pj.tbike.core.util.SimpleCrudService;

import java.time.Duration;
import java.util.Optional;

public class UserServiceImpl
        extends SimpleCrudService<User, Long, UserRepository>
        implements UserService {

    private static final int      POOL_SIZE     = 10;
    private static final Duration MAX_LIFE_TIME = Duration.ofMinutes( 10 );

    private final CacheManager<String> cacheManager;

    public UserServiceImpl(UserRepository repository) {
        super( repository );
        cacheManager = createCacheManager();
    }

    private CacheManager<String> createCacheManager() {
        var scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize( POOL_SIZE );
        scheduler.initialize();
        var controller = new CacheControl( scheduler, MAX_LIFE_TIME );
        return new CacheManager<>( controller );
    }

    public Optional<User> findByUsername(String username) {
        Cache cache = cacheManager.get( username );
        if ( cache.isPresent() ) {
            return Optional.of( new User().fromCacheObject( cache.get() ) );
        }
        var user = getRepository().findByUsername( username );
        user.ifPresent( value -> cacheManager.cache(
                username,
                value.toCacheObject()
        ) );
        return user;
    }
}

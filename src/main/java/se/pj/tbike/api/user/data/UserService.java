package se.pj.tbike.api.user.data;

import com.ank.japi.HttpStatus;
import com.ank.japi.exception.HttpException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.pj.tbike.api.user.entity.User;
import se.pj.tbike.caching.Cache;
import se.pj.tbike.caching.CacheControl;
import se.pj.tbike.caching.CacheManager;
import se.pj.tbike.impl.SimpleCrudService;
import se.pj.tbike.service.CrudService;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class UserService
        extends SimpleCrudService<User, Long, UserRepository>
        implements CrudService<User, Long>, UserDetailsService {

    private static final int POOL_SIZE = 10;
    private static final Duration MAX_LIFE_TIME = Duration.ofMinutes(10);

    private final CacheManager<String> cacheManager;

    public UserService(UserRepository repository) {
        super(repository);
        this.cacheManager = createCacheManager();
    }

    private CacheManager<String> createCacheManager() {
        var scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(POOL_SIZE);
        scheduler.initialize();
        var controller = new CacheControl(scheduler, MAX_LIFE_TIME);
        return new CacheManager<>(controller);
    }

    public Optional<User> findByUsername(String username) {
        Cache cache = cacheManager.get(username);
        if (cache.isPresent()) {
            return Optional.of(new User().fromCacheObject(cache.get()));
        }
        Optional<User> user = repository.findByUsername(username);
        user.ifPresent(value -> cacheManager.cache(
                username,
                value.toCacheObject()
        ));
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier<RuntimeException> ex = () -> new HttpException(
                HttpStatus.NOT_FOUND,
                "User with [" + username + "] not found"
        );
        return findByUsername(username).orElseThrow(ex);
    }
}

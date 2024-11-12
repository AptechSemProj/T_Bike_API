package se.pj.tbike.core.api.user.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import se.pj.tbike.core.api.user.entity.User;
import se.pj.tbike.core.util.SimpleCrudService;

public class UserServiceImpl
        extends SimpleCrudService<User, Long, UserRepository>
        implements UserService {

    public UserServiceImpl(UserRepository repository) {
        super( repository );
    }

    @Override
    public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
        return getRepository()
                .findByUsername( username )
                .orElseThrow( () -> new UsernameNotFoundException(
                        "username not found"
                ) );
    }
}

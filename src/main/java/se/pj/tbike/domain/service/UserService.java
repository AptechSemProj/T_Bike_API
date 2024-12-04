package se.pj.tbike.domain.service;

import com.ank.japi.exception.HttpException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.pj.tbike.domain.repository.UserRepository;
import se.pj.tbike.domain.entity.User;
import se.pj.tbike.impl.SimpleCrudService;
import se.pj.tbike.common.service.CrudService;

@Service
public class UserService
        extends SimpleCrudService<User, Long, UserRepository>
        implements CrudService<User, Long>, UserDetailsService {

    public UserService(UserRepository repository) {
        super(repository);
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> HttpException.notFound(
                        "User with [" + username + "] not found"
                ));
    }
}

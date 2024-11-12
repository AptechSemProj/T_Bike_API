package se.pj.tbike.core.api.user.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import se.pj.tbike.core.api.user.data.UserRepository;
import se.pj.tbike.core.api.user.data.UserService;
import se.pj.tbike.core.api.user.data.UserServiceImpl;

@Configuration
public class UserServiceConf {

    @Bean
    public UserDetailsService userDetailsService(UserService service) {
        return service;
    }

    @Bean
    public UserService userService(UserRepository repository) {
        return new UserServiceImpl( repository );
    }

}

package se.pj.tbike.core.api.auth.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import se.pj.tbike.core.api.user.data.UserService;

@Configuration
public class AuthServiceConf {

    @Bean
    public UserDetailsService userDetailsService(UserService service) {
        return username -> service
                .findByUsername( username )
                .orElseThrow( () -> new UsernameNotFoundException(
                        "username not found"
                ) );
    }
}

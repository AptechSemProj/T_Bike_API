package se.pj.tbike.core.api.auth.conf;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import se.pj.tbike.core.api.user.entity.User;
import se.pj.tbike.core.api.auth.service.jwt.JwtFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConf {

//                                new AntPathRequestMatcher(
//                                        HttpMethod.GET.name()
//                                ),

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtFilter filter,
            AuthenticationProvider provider
    ) throws Exception {
        /*.cors(AbstractHttpConfigurer::disable)*/
        http.csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(provider)
                .addFilterBefore(
                        filter, UsernamePasswordAuthenticationFilter.class
                )
                .sessionManagement(sc -> sc.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(r -> r
                                .requestMatchers(
                                        "/api/brands/**",
                                        "/api/categories/**",
                                        "/api/products/**",
                                        "/api/images/**"
                                )
                                .permitAll()
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/auth/**",
                                        "/api/products/list",
                                        "/api/products/search"
                                )
                                .permitAll()
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/brands",
                                        "/api/categories",
                                        "/api/products",
                                        "/api/images/**"
                                )
                                .hasRole(User.Role.ADMIN.name())
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/brands/**",
                                        "/api/categories/**",
                                        "/api/products/**"
                                )
                                .hasRole(User.Role.ADMIN.name())
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/brands/**",
                                        "/api/categories/**",
                                        "/api/products/**",
                                        "/api/images/**"
                                )
                                .hasRole(User.Role.ADMIN.name())
                                .anyRequest()
                                .permitAll()
//                        .authenticated()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            UserDetailsService service,
            PasswordEncoder encoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }
}

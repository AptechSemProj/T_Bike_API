package se.pj.tbike.config;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import se.pj.tbike.api.brand.controller.QueryBrandController;
import se.pj.tbike.api.order.controller.CreateOrderController;
import se.pj.tbike.api.orderdetail.controller.CreateDetailController;
import se.pj.tbike.api.user.entity.Role;
import se.pj.tbike.jwt.JwtFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConf {

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
                                HttpMethod.GET,
                                QueryBrandController.API_URL,
                                "/api/brands/{id}",
                                "/api/categories/**",
                                "/api/products/**",
                                "/api/images/**"
                        )
                        .permitAll()
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/auth/**",
                                "/api/products/list/**",
                                "/api/products/search/**"
                        )
                        .permitAll()
                        .requestMatchers(
                                HttpMethod.POST,
                                CreateOrderController.API_URL,
                                CreateDetailController.API_URL
                        )
                        .hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/brands/**",
                                "/api/categories/**",
                                "/api/products/**",
                                "/api/images/**"
                        )
                        .hasRole(Role.ADMIN.name())
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/brands/**",
                                "/api/categories/**",
                                "/api/products/**"
                        )
                        .hasRole(Role.ADMIN.name())
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/brands/**",
                                "/api/categories/**",
                                "/api/products/**",
                                "/api/images/**"
                        )
                        .hasRole(Role.ADMIN.name())
                        .anyRequest()
                        .permitAll()
                );
//                        .authenticated()
        return http.build();
    }

    @Bean
    public Authentication authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
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

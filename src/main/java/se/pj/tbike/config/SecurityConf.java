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
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import se.pj.tbike.http.controller.category.QueryCategoryController;
import se.pj.tbike.http.controller.admin.orderdetail.CreateDetailController;
import se.pj.tbike.domain.entity.User;
import se.pj.tbike.http.Routes;
import se.pj.tbike.jwt.JwtFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConf {

    private void allAccess(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>
                    .AuthorizationManagerRequestMatcherRegistry registry
    ) {
        registry.requestMatchers(
                        HttpMethod.GET,
                        Routes.QUERY_BRAND_PATH,
                        Routes.FIND_BRAND_PATH,
                        QueryCategoryController.API_URL,
                        Routes.FIND_CATEGORY_PATH,
                        "/api/products/**",
                        "/api/images/**"
                ).permitAll()
                .requestMatchers(
                        HttpMethod.POST,
                        Routes.AUTH_LOGIN_PATH,
                        Routes.AUTH_REGISTER_PATH,
                        "/api/products/list/**",
                        "/api/products/search/**"
                ).permitAll();
    }

    private void adminAccess(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>
                    .AuthorizationManagerRequestMatcherRegistry registry
    ) {
        String adminRole = User.Role.ADMIN.name();
        registry.requestMatchers(
                        HttpMethod.GET,
                        Routes.FIND_ORDER_PATH
                ).hasRole(adminRole)
                .requestMatchers(
                        HttpMethod.POST,
                        Routes.CREATE_BRAND_PATH,
                        Routes.CREATE_CATEGORY_PATH,
                        "/api/products/**",
                        "/api/images/**"
                ).hasRole(adminRole)
                .requestMatchers(
                        HttpMethod.PUT,
                        Routes.UPDATE_BRAND_PATH,
                        Routes.UPDATE_CATEGORY_PATH,
                        "/api/products/**"
                ).hasRole(adminRole)
                .requestMatchers(
                        HttpMethod.DELETE,
                        Routes.DELETE_BRAND_PATH,
                        Routes.DELETE_CATEGORY_PATH,
                        "/api/products/**",
                        "/api/images/**"
                ).hasRole(adminRole);
    }

    private void userAccess(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>
                    .AuthorizationManagerRequestMatcherRegistry registry
    ) {
        registry.requestMatchers(
                        HttpMethod.GET,
                        Routes.GET_CART_PATH
                ).hasRole(User.Role.USER.name())
                .requestMatchers(
                        HttpMethod.POST,
                        CreateDetailController.API_URL
                ).hasAnyRole(User.Role.ADMIN.name(), User.Role.USER.name());
    }

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
                // all access
                .authorizeHttpRequests(this::allAccess)
                // admin access
                .authorizeHttpRequests(this::adminAccess)
                // user access
                .authorizeHttpRequests(this::userAccess)
                // close config
                .authorizeHttpRequests(r -> r.anyRequest().permitAll());
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

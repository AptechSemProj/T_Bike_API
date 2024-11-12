package se.pj.tbike.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter
        extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX        = "Bearer ";

    private final JwtService         jwtService;
    private final UserDetailsService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String auth = request.getHeader( AUTHORIZATION_HEADER );
        if ( auth == null || !auth.startsWith( BEARER_PREFIX ) ) {
            filterChain.doFilter( request, response );
            return;
        }
        final String token = auth.substring( BEARER_PREFIX.length() );
        final String username = jwtService.extractUsername( token );
        SecurityContext context = SecurityContextHolder.getContext();
        if ( username != null && context.getAuthentication() == null ) {
            UserDetails user = userService.loadUserByUsername( username );
            if ( jwtService.isTokenValid( token, user ) ) {
                UsernamePasswordAuthenticationToken jwtToken =
                        new UsernamePasswordAuthenticationToken(
                                user, null, user.getAuthorities()
                        );
                jwtToken.setDetails( new WebAuthenticationDetails( request ) );
                context.setAuthentication( jwtToken );
            }
        }
        filterChain.doFilter( request, response );
    }
}

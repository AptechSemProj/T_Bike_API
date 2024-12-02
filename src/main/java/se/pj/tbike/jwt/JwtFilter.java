package se.pj.tbike.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import se.pj.tbike.http.controller.auth.LoginController;
import se.pj.tbike.http.controller.auth.RegisterController;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String path = request.getServletPath();
        if (path.contains(LoginController.API_URL)
                || path.contains(RegisterController.API_URL)) {
            filterChain.doFilter(request, response);
            return;
        }
        final String auth = request.getHeader("Authorization");
        final String jwt = TokenType.extractToken(auth);
        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }
        JwtToken parsedToken = jwtService.parseToken(jwt);
        if (parsedToken.username() != null && SecurityContextHolder
                .getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(
                    parsedToken.username()
            );
            if (parsedToken.isValid(userDetails)) {
                var token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                token.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }
        filterChain.doFilter(request, response);
    }
}

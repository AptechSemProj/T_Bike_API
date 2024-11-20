package se.pj.tbike.core.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.pj.tbike.core.api.auth.dto.AuthResponse;
import se.pj.tbike.core.api.auth.dto.LoginRequest;
import se.pj.tbike.core.api.auth.dto.RegisterRequest;
import se.pj.tbike.core.api.auth.service.jwt.JwtService;
import se.pj.tbike.core.api.user.data.UserService;
import se.pj.tbike.core.api.user.entity.Role;
import se.pj.tbike.core.api.user.entity.User;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder       passwordEncoder;
    private final JwtService jwtService;
    private final UserService           userService;

    public AuthResponse register(RegisterRequest req) {
        User user = new User();
        Role role = Role.USER;
        try {
            role = Role.valueOf( req.getRole().toUpperCase() );
        }
        catch ( RuntimeException ignored ) {
        }
        user.setRole( role );
        user.setUsername( req.getUsername() );
        user.setPassword( passwordEncoder.encode( req.getPassword() ) );
        user.setName( req.getName() );
        user.setAvatarImage( null );
        user.setPhoneNumber( req.getPhoneNumber() );
        userService.create( user );
        String token = jwtService.generateToken( user );
        return new AuthResponse( token );
    }

    public AuthResponse login(LoginRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUsername(),
                        req.getPassword()
                )
        );
        User user = userService
                .findByUsername( req.getUsername() )
                .orElseThrow( () -> new UsernameNotFoundException(
                        req.getUsername()
                ) );
        String token = jwtService.generateToken( user );
        return new AuthResponse( token );
    }

}

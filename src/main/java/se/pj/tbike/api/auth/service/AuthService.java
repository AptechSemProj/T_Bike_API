package se.pj.tbike.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.pj.tbike.api.auth.dto.AuthResponse;
import se.pj.tbike.api.auth.dto.LoginRequest;
import se.pj.tbike.api.auth.dto.RegisterRequest;
import se.pj.tbike.jwt.JwtService;
import se.pj.tbike.api.user.data.UserService;
import se.pj.tbike.api.user.entity.User;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager manager;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthResponse register(RegisterRequest req) {
        User user = new User();
        user.setRole(req.getRole());
        user.setUsername(req.getUsername());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setName(req.getName());
        user.setAvatarImage(null);
        user.setPhoneNumber(req.getPhoneNumber());
        User created = userService.create(user);
        String token = jwtService.generateToken(created);
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest req) {
        String username = req.getUsername();
        var auth = new UsernamePasswordAuthenticationToken(
                username, req.getPassword()
        );
        Authentication authentication = manager.authenticate(auth);
        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

}

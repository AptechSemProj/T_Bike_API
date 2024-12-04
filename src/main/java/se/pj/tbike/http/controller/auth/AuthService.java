package se.pj.tbike.http.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.pj.tbike.jwt.JwtService;
import se.pj.tbike.domain.service.UserService;
import se.pj.tbike.domain.entity.User;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager manager;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final UserService userService;

    public String register(User user, String password) {
        user.setPassword(encoder.encode(password));
        User created = userService.create(user);
        return jwtService.generateToken(created);
    }

    public String login(String username, String password) {
        Authentication auth = tryLogin(username, password);
        User user = (User) auth.getPrincipal();
        return jwtService.generateToken(user);
    }

    private Authentication tryLogin(String username, String password) {
        return manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username, password
                )
        );
    }

    public String changePassword(
            String username,
            String oldPassword,
            String newPassword
    ) {
        Authentication auth = tryLogin(username, oldPassword);
        User user = (User) auth.getPrincipal();
        user.setPassword(encoder.encode(newPassword));
        userService.update(user);
        return jwtService.generateToken(user);
    }

}

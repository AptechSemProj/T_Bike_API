package se.pj.tbike.http.controller.auth;

import com.ank.japi.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.http.Routes;
import se.pj.tbike.http.model.auth.AuthResponse;
import se.pj.tbike.http.model.auth.LoginRequest;
import se.pj.tbike.impl.BaseController;

@RequestMapping(Routes.AUTH_LOGIN_PATH)
@RestController
public class LoginController extends BaseController {

    private final AuthService service;

    public LoginController(
            ResponseConfigurer configurer,
            AuthService service
    ) {
        super(configurer);
        this.service = service;
    }

    @PostMapping({"", "/"})
    public Response login(@RequestBody LoginRequest req) {
        return tryCatch(() -> {
            String token = service.login(
                    req.getUsername(),
                    req.getPassword()
            );
            return ok(new AuthResponse(token));
        });
    }
}

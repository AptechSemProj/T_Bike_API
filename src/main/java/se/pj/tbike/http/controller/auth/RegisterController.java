package se.pj.tbike.http.controller.auth;

import com.ank.japi.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.domain.entity.User;
import se.pj.tbike.http.Routes;
import se.pj.tbike.http.model.auth.AuthResponse;
import se.pj.tbike.http.model.auth.RegisterRequest;
import se.pj.tbike.impl.BaseController;

@RequestMapping(Routes.AUTH_REGISTER_PATH)
@RestController
public class RegisterController extends BaseController {

    private final AuthService service;

    public RegisterController(
            ResponseConfigurer configurer,
            AuthService service
    ) {
        super(configurer);
        this.service = service;
    }

    @PostMapping({"", "/"})
    public Response register(@RequestBody RegisterRequest req) {
        return tryCatch(() -> {
            User user = new User();
            user.setName(req.getName());
            user.setPhoneNumber(req.getPhoneNumber());
            user.setRole(req.getRole());
            user.setUsername(req.getUsername());
            user.setAvatarImage(null);
            String token = service.register(user, req.getPassword());
            return ok(new AuthResponse(token));
        });
    }
}

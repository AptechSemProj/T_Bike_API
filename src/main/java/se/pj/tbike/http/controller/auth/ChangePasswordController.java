package se.pj.tbike.http.controller.auth;

import com.ank.japi.Response;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.http.Routes;
import se.pj.tbike.http.model.auth.AuthResponse;
import se.pj.tbike.http.model.auth.ChangePasswordRequest;
import se.pj.tbike.impl.BaseController;

@RequestMapping(Routes.AUTH_CHANGE_PASSWORD_PATH)
@PreAuthorize("hasRole('USER')")
@RestController
public class ChangePasswordController extends BaseController {

    private final AuthService authService;

    public ChangePasswordController(
            ResponseConfigurer configurer,
            AuthService authService
    ) {
        super(configurer);
        this.authService = authService;
    }

    @PutMapping({"", "/"})
    public Response changePassword(
            @RequestBody ChangePasswordRequest req
    ) {
        return tryCatch(() -> {
            String token = authService.changePassword(
                    req.username(),
                    req.oldPassword(),
                    req.newPassword()
            );
            return ok(new AuthResponse(token));
        });
    }

}

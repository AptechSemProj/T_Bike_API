package se.pj.tbike.http.controller.auth;

import com.ank.japi.RequestHandler;
import com.ank.japi.Response;
import com.ank.japi.impl.StdRequestHandler;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.impl.ResponseConfigurerImpl;

@RequestMapping(LoginController.API_URL)
@RestController
@AllArgsConstructor
public class LoginController {

    public static final String API_URL = "/api/auth/authenticate";

    private static final
    RequestHandler<LoginRequest, AuthResponse> HANDLER;

    static {
        HANDLER = new StdRequestHandler<>(ResponseConfigurerImpl::new);
    }

    private final AuthService service;

    @PostMapping({"", "/"})
    public Response login(
            @RequestBody LoginRequest request
    ) {
        return HANDLER.handle(
                request,
                (res, req) -> res.ok(service.login(req))
        );
    }
}

package se.pj.tbike.api.auth.controller;

import com.ank.japi.RequestHandler;
import com.ank.japi.Response;
import com.ank.japi.impl.StdRequestHandler;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.api.auth.dto.AuthResponse;
import se.pj.tbike.api.auth.dto.RegisterRequest;
import se.pj.tbike.api.auth.service.AuthService;
import se.pj.tbike.impl.ResponseConfigurerImpl;

@RequestMapping(RegisterController.API_URL)
@RestController
@AllArgsConstructor
public class RegisterController {

    public static final String API_URL = "/api/auth/register";

    private static final
    RequestHandler<RegisterRequest, AuthResponse> HANDLER;

    static {
        HANDLER = new StdRequestHandler<>(ResponseConfigurerImpl::new);
    }

    private final AuthService service;

    @PostMapping({"", "/"})
    public Response<AuthResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return HANDLER.handle(
                request,
                (res, req) -> res.created(service.register(req))
        );
    }
}

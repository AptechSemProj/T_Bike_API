package se.pj.tbike.core.api.auth.controller;

import com.ank.japi.Response;
import com.ank.japi.impl.StdRequestHandler;
import com.ank.japi.validation.Requirements;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.core.api.auth.conf.AuthApiUrls;
import se.pj.tbike.core.api.auth.dto.AuthResponse;
import se.pj.tbike.core.api.auth.dto.LoginRequest;
import se.pj.tbike.core.api.auth.dto.RegisterRequest;
import se.pj.tbike.core.api.auth.service.AuthService;
import se.pj.tbike.core.japi.impl.ResponseConfigurerImpl;

@RequestMapping(AuthApiUrls.AUTH_URL)
@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping({ "/register" })
    public Response<AuthResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return new StdRequestHandler<RegisterRequest, AuthResponse>(
                ResponseConfigurerImpl::new
        ).handle(
                request,
                (res, body, p) -> res.ok( authService.register( body ) ),
                (body, validator) -> {
                    validator.bind( "username", request.getUsername() )
                             .require(
                                     Requirements.notBlank( 100 )
                             );
                    validator.bind( "password", request.getPassword() )
                             .require(
                                     Requirements.notBlank( 60 )
                             );
                    validator.bind( "name", request.getName() )
                             .require(
                                     Requirements.notBlank( 100 )
                             );
                    validator.bind( "phoneNumber", request.getPhoneNumber() )
                             .require(
                                     Requirements.notBlank( 10 )
                             );
                }
        );
    }

    @PostMapping({ "/authenticate" })
    public Response<AuthResponse> login(@RequestBody LoginRequest request) {
        return new StdRequestHandler<LoginRequest, AuthResponse>(
                ResponseConfigurerImpl::new
        ).handle(
                request,
                (res, req, query) -> res.ok( authService.login( req ) ),
                (body, validator) -> {
                    validator.bind( "username", request.getUsername() )
                             .require(
                                     Requirements.notBlank( 100 )
                             );
                    validator.bind( "password", request.getPassword() )
                             .require(
                                     Requirements.notBlank( 60 )
                             );
                }
        );
    }
}

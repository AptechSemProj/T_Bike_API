package se.pj.tbike.core.api.auth.dto;

import com.ank.japi.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest
        implements Request<AuthResponse> {

    private String username;

    private String password;

}

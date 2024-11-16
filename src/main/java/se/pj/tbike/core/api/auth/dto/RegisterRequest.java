package se.pj.tbike.core.api.auth.dto;

import com.ank.japi.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest
        implements Request<AuthResponse> {

    private String username;

    private String password;

    private String name;

    private String phoneNumber;

    private String role;

}

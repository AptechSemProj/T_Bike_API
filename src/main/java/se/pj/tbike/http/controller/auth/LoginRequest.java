package se.pj.tbike.http.controller.auth;

import com.ank.japi.HttpStatus;
import com.ank.japi.Request;
import com.ank.japi.exception.HttpException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginRequest
        implements Request<AuthResponse> {

    private final String username;

    private final String password;

    public String getUsername() {
        if (username == null) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "Username is required."
            );
        }
        if (username.isBlank()) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "Username cannot be empty."
            );
        }
        if (username.length() > 100) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "Username is too long (100 characters)."
            );
        }
        return username;
    }

    public String getPassword() {
        if (password == null) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "Password is required."
            );
        }
        if (password.isBlank()) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "Password cannot be empty."
            );
        }
        if (password.length() > 60) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "Password is too long (60 characters)."
            );
        }
        return password;
    }
}

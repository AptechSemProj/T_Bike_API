package se.pj.tbike.http.model.auth;

import com.ank.japi.exception.HttpException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginRequest {

    private final String username;

    private final String password;

    public String getUsername() {
        if (username == null) {
            throw HttpException.badRequest(
                    "Username is required."
            );
        }
        if (username.isBlank()) {
            throw HttpException.badRequest(
                    "Username cannot be empty."
            );
        }
        if (username.length() > 100) {
            throw HttpException.badRequest(
                    "Username is too long (100 characters)."
            );
        }
        return username;
    }

    public String getPassword() {
        if (password == null) {
            throw HttpException.badRequest(
                    "Password is required."
            );
        }
        if (password.isBlank()) {
            throw HttpException.badRequest(
                    "Password cannot be empty."
            );
        }
        if (password.length() > 60) {
            throw HttpException.badRequest(
                    "Password is too long (60 characters)."
            );
        }
        return password;
    }
}

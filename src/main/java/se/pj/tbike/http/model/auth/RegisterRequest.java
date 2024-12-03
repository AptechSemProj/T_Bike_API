package se.pj.tbike.http.model.auth;

import com.ank.japi.exception.HttpException;
import se.pj.tbike.domain.entity.User;

public class RegisterRequest
        extends LoginRequest {

    private final String name;

    private final String phoneNumber;

    private final String role;

    public RegisterRequest(String username, String password,
                           String name, String phoneNumber,
                           String role) {
        super(username, password);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public String getName() {
        if (name == null) {
            throw HttpException.badRequest(
                    "Name is required."
            );
        }
        if (name.isBlank()) {
            throw HttpException.badRequest(
                    "Name cannot be empty."
            );
        }
        if (name.length() > 100) {
            throw HttpException.badRequest(
                    "Name is too long (100 characters)."
            );
        }
        return name;
    }

    public String getPhoneNumber() {
        if (phoneNumber == null) {
            throw HttpException.badRequest(
                    "Phone number is required."
            );
        }
        if (phoneNumber.isBlank()) {
            throw HttpException.badRequest(
                    "Phone number cannot be empty."
            );
        }
        if (phoneNumber.length() > 100) {
            throw HttpException.badRequest(
                    "Phone number is too long (100 characters)."
            );
        }
        return phoneNumber;
    }

    public User.Role getRole() {
        try {
            return User.Role.valueOf(role.toUpperCase());
        } catch (Throwable throwable) {
            return User.Role.USER;
        }
    }

}

package se.pj.tbike.http.controller.auth;

import com.ank.japi.HttpStatus;
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
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "Name is required."
            );
        }
        if (name.isBlank()) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "Name cannot be empty."
            );
        }
        if (name.length() > 100) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "Name is too long (100 characters)."
            );
        }
        return name;
    }

    public String getPhoneNumber() {
        if (phoneNumber == null) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "Phone number is required."
            );
        }
        if (phoneNumber.isBlank()) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "Phone number cannot be empty."
            );
        }
        if (phoneNumber.length() > 100) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
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

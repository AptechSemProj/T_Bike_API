package se.pj.tbike.api.auth.dto;

import com.ank.japi.HttpStatus;
import com.ank.japi.exception.HttpException;
import se.pj.tbike.api.user.entity.Role;

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

    public Role getRole() {
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (Throwable throwable) {
            return Role.USER;
        }
    }

}

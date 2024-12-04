package se.pj.tbike.http.model.auth;

import com.ank.japi.exception.HttpException;

public record ChangePasswordRequest(
        String username,
        String oldPassword,
        String newPassword
) {

    @Override
    public String username() {
        if (username == null) {
            throw HttpException.badRequest(
                    "Username is required to change password."
            );
        }
        if (username.isBlank()) {
            throw HttpException.badRequest(
                    "Username must not be blank."
            );
        }
        return username;
    }

    @Override
    public String oldPassword() {
        if (oldPassword == null) {
            throw HttpException.badRequest(
                    "Old password is required to change password."
            );
        }
        if (oldPassword.isBlank()) {
            throw HttpException.badRequest(
                    "Old password must not be blank."
            );
        }
        return oldPassword;
    }

    @Override
    public String newPassword() {
        if (newPassword == null) {
            throw HttpException.badRequest(
                    "New password is required to change password."
            );
        }
        if (newPassword.isBlank()) {
            throw HttpException.badRequest(
                    "New password must not be blank."
            );
        }
        return newPassword;
    }
}

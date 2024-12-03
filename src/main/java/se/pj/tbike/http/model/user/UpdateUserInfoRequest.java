package se.pj.tbike.http.model.user;

import com.ank.japi.exception.HttpException;

public record UpdateUserInfoRequest(
        String name,
        String phoneNumber,
        String avatar
) {
    @Override
    public String name() {
        if (name == null) {
            return null;
        }
        if (name.isBlank()) {
            throw HttpException.badRequest(
                    "Name cannot be blank."
            );
        }
        return name;
    }

    @Override
    public String phoneNumber() {
        if (phoneNumber == null) {
            return null;
        }
        if (phoneNumber.isBlank()) {
            throw HttpException.badRequest(
                    "Phone number cannot be blank."
            );
        }
        return phoneNumber;
    }

    public String avatar() {
        if (avatar == null) {
            return null;
        }
        if (avatar.isBlank()) {
            throw HttpException.badRequest(
                    "Avatar image cannot be blank."
            );
        }
        return avatar;
    }
}
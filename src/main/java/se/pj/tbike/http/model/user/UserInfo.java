package se.pj.tbike.http.model.user;

import java.io.Serializable;

public record UserInfo(Long id, String name, String phoneNumber, String avatarImage)
        implements Serializable {
}
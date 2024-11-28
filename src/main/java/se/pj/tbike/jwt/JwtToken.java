package se.pj.tbike.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public record JwtToken(String username, boolean isExpired) {

    public boolean isValid(UserDetails userDetails) {
        if (isExpired) {
            return false;
        }
        if (username == null) {
            return false;
        }
        return username.equals(userDetails.getUsername());
    }

}

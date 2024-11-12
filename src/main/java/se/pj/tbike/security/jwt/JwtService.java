package se.pj.tbike.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "afe037d4197e043959f6ac4dde6c0ffa00e27a8837af537a16facb4b625b9991";

    /* In second */
    private static final long EXPIRATION_TIME = 60 * 10;

    public String generateToken(UserDetails userDetails) {
        return generateToken( new HashMap<>(), userDetails );
    }

    public String generateToken(
            Map<String, Object> claims,
            UserDetails userDetails
    ) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                   .setClaims( claims )
                   .setSubject( userDetails.getUsername() )
                   .setIssuedAt( new Date( now ) )
                   .setExpiration( new Date( now + (1000 * EXPIRATION_TIME) ) )
                   .signWith( getKey(), SignatureAlgorithm.HS256 )
                   .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername( token );
        return (username.equals( userDetails.getUsername() ))
                && !isTokenExpired( token );
    }

    private boolean isTokenExpired(String token) {
        Date extracted = extractExpiration( token );
        if ( extracted == null ) {
            throw new BadCredentialsException( "Invalid token" );
        }
        return extracted.before( new Date() );
    }

    public String extractUsername(String token) {
        return extractClaim( token, Claims::getSubject );
    }

    private Date extractExpiration(String token) {
        return extractClaim( token, Claims::getExpiration );
    }

    public <T> T extractClaim(
            String token, Function<Claims, T> claimsResolver
    ) {
        final Claims claims = extractAllClaims( token );
        return claimsResolver.apply( claims );
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey( getKey() )
                   .build()
                   .parseClaimsJws( token )
                   .getBody();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode( SECRET_KEY );
        return Keys.hmacShaKeyFor( keyBytes );
    }
}

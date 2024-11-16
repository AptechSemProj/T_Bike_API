package se.pj.tbike.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long   jwtExpiration;

    public String generateToken(UserDetails userDetails) {
        return generateToken( new HashMap<>(), userDetails );
    }

    public String generateToken(
            Map<String, Object> claims,
            UserDetails userDetails
    ) {
        return Jwts.builder()
                   .setClaims( claims )
                   .setSubject( userDetails.getUsername() )
                   .setIssuedAt( new Date( System.currentTimeMillis() ) )
                   .setExpiration( new Date(
                           System.currentTimeMillis() + jwtExpiration
                   ) )
                   .signWith( getKey(), SignatureAlgorithm.HS256 )
                   .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey( getKey() )
                   .build()
                   .parseClaimsJws( token )
                   .getBody();
    }

    private boolean isTokenExpired(Claims claims) {
        Date exp = claims.getExpiration();
        if ( exp == null ) {
            throw new BadCredentialsException( "Invalid token" );
        }
        return exp.before( new Date() );
    }

    public JwtToken parseToken(String token) {
        Claims claims = extractAllClaims( token );
        return new JwtToken( claims.getSubject(), isTokenExpired( claims ) );
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode( secretKey );
        return Keys.hmacShaKeyFor( keyBytes );
    }

    public record JwtToken(String username, boolean isExpired) {
        public boolean isValid(UserDetails userDetails) {
            if ( isExpired ) {
                return false;
            }
            if ( username == null ) {
                return false;
            }
            return username.equals( userDetails.getUsername() );
        }
    }

    public enum TokenType {
        BEARER( "Bearer " ),
        ;
        private final String prefix;

        TokenType(String prefix) {
            this.prefix = prefix;
        }

        public static String extractToken(String header) {
            if ( header == null ) {
                return null;
            }
            TokenType[] types = values();
            for ( TokenType type : types ) {
                if ( header.startsWith( type.prefix ) ) {
                    return header.substring( type.prefix.length() );
                }
            }
            return null;
        }
    }
}
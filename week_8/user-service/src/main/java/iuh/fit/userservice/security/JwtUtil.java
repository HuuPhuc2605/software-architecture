package iuh.fit.userservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey key;

    public JwtUtil(@Value("${jwt.secret:very-secret-key-change-me}") String secret) {
        // for simplicity, derive key from provided secret bytes (not for prod)
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String userId, String username) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(userId)
                .claim("username", username)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 1000L * 60 * 60 * 24))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}

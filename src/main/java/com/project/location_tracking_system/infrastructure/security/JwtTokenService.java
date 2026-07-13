package com.project.location_tracking_system.infrastructure.security;

import com.project.location_tracking_system.domain.model.User;
import com.project.location_tracking_system.domain.ports.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtTokenService implements TokenService {

    private final SecretKey secretKey;

    private final long expiration;

    public JwtTokenService(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));;
        this.expiration = expiration;
    }

    @Override
    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.userId())
                .claim("username", user.username())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String extractUserId(String token) {
        return extractClaims(token).getSubject();
    }

    @Override
    public String extractUsername(String token) {
        return extractClaims(token).get("username", String.class);
    }

    @Override
    public boolean isValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    private Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

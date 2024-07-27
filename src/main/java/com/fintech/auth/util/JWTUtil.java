package com.fintech.auth.util;

import com.fintech.auth.model.AuthUser;
import com.fintech.auth.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JWTUtil {

    private SecretKey key;

    @Value("${auth.secret.string}")
    private String secret;

    @Value("${auth.secret.algorithm}")
    private String algorithm;

    @Value("${auth.secret.access-expiration-time}")
    private long expirationTime;

    @Value("${auth.secret.refresh-expiration-time}")
    private long refreshExpirationTime;

    @PostConstruct
    private void init() {
        byte[] secretBytes = Base64.getDecoder().decode(secret.getBytes(StandardCharsets.UTF_8));
        this.key = new SecretKeySpec(secretBytes, algorithm);
    }

    public String generateAccessToken(AuthUser userDetails) {
        Set<String> roles = userDetails.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toSet());
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("roles", roles)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(HashMap<String, Object> claims, AuthUser userDetails) {
        Set<String> roles = userDetails.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toSet());
        return Jwts.builder()
                .claims(claims)
                .claim("roles", roles)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime + refreshExpirationTime))
                .signWith(key)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload());
    }

}

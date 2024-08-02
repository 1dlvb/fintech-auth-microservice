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
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utility class for handling JWT operations such as generating and validating tokens.
 * @author Matushkin Anton
 */
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

    /**
     * Generates an access token for the given user details.
     * @param userDetails The user details.
     * @return The generated access token.
     */
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

    /**
     * Generates a refresh token for the given user details.
     * @param claims Additional claims to include in the token.
     * @param userDetails The user details.
     * @return The generated refresh token.
     */
    public String generateRefreshToken(Map<String, Object> claims, AuthUser userDetails) {
        Set<String> roles = userDetails.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toSet());
        return Jwts.builder()
                .claims(claims)
                .claim("roles", roles)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshExpirationTime))
                .signWith(key)
                .compact();
    }

    /**
     * Extracts the username from the given token.
     * @param token The token.
     * @return The username.
     */
    public String getUsernameFromToken(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    /**
     * Checks if the given token is expired.
     * @param token The token.
     * @return True if the token is expired, false otherwise.
     */
    public boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    /**
     * Validates the token against the user details.
     * @param token The token.
     * @param userDetails The user details.
     * @return True if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Extracts claims from the given token using the provided function.
     * @param token The token.
     * @param claimsTFunction The function to apply on the claims.
     * @param <T> The type of the claim.
     * @return The extracted claim.
     */
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload());
    }

}

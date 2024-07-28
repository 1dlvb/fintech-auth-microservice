package com.fintech.auth.util;

import com.fintech.auth.model.AuthUser;
import com.fintech.auth.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class JWTUtilTests {

    private JWTUtil jwtUtil;
    private AuthUser userDetails;

    @BeforeEach
    void setUp() {
        jwtUtil = new JWTUtil();

        String secret = "super_long_and_secure_secret_string";
        String algorithm = "HmacSHA256";
        long expirationTime = 180000;
        long refreshExpirationTime = 180000*2;

        ReflectionTestUtils.setField(jwtUtil, "secret",
                Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8)));
        ReflectionTestUtils.setField(jwtUtil, "algorithm", algorithm);
        ReflectionTestUtils.setField(jwtUtil, "expirationTime", expirationTime);
        ReflectionTestUtils.setField(jwtUtil, "refreshExpirationTime", refreshExpirationTime);

        try {
            var initMethod = JWTUtil.class.getDeclaredMethod("init");
            initMethod.setAccessible(true);
            initMethod.invoke(jwtUtil);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Role role = new Role();
        role.setId("USER");
        userDetails = new AuthUser();
        userDetails.setUsername("test");
        userDetails.setRoles(Set.of(role));
    }

    @Test
    void testGenerateAccessTokenNotNull() {
        assertThat(jwtUtil.generateAccessToken(userDetails)).isNotNull();
    }

    @Test
    void testGenerateRefreshTokenNotNull() {
        assertThat(jwtUtil.generateRefreshToken(new HashMap<>(), userDetails)).isNotNull();
    }

    @Test
    void testGetUsernameFromTokenReturnsUsernameExtractedFromToken() {
        String token = jwtUtil.generateAccessToken(userDetails);
        String username = jwtUtil.getUsernameFromToken(token);
        assertEquals(userDetails.getUsername(), username);
    }

    @Test
    void testIsTokenExpiredReturnsFalseWhenTokenIsNotExpired() {
        String token = jwtUtil.generateAccessToken(userDetails);
        assertFalse(jwtUtil.isTokenExpired(token));
    }

    @Test
    void testIsTokenValidReturnsTrueWhenTokenIsValid() {
        String token = jwtUtil.generateAccessToken(userDetails);
        assertTrue(jwtUtil.isTokenValid(token, userDetails));
    }

}
package com.fintech.auth.service.impl;

import com.fintech.auth.dto.AuthDTO;
import com.fintech.auth.dto.RefreshDTO;
import com.fintech.auth.dto.UserSignInDTO;
import com.fintech.auth.dto.UserSignUpDTO;
import com.fintech.auth.model.AuthUser;
import com.fintech.auth.model.Role;
import com.fintech.auth.repository.AuthUserRepository;
import com.fintech.auth.repository.RoleRepository;
import com.fintech.auth.util.JWTUtil;
import com.fintech.auth.util.RolesEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTests {

    @Mock
    private AuthUserRepository authUserRepository;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(
                authUserRepository,
                jwtUtil,
                passwordEncoder,
                authenticationManager,
                roleRepository
        );
    }

    @Test
    void testSignUpCreatesUser() {
        UserSignUpDTO userDTO = new UserSignUpDTO();
        userDTO.setUsername("test");
        userDTO.setEmail("test@test.abc");
        userDTO.setPassword("password");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.findById(RolesEnum.USER.name())).thenReturn(Optional.of(
                new Role("USER", "description", true)));
        when(authUserRepository.save(any(AuthUser.class))).thenReturn(new AuthUser());

        AuthDTO response = authService.signup(userDTO);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("User saved successfully.");

        verify(authUserRepository, times(1)).save(any(AuthUser.class));
    }

    @Test
    void testSignInReturnsProperResponse() {
        UserSignInDTO userSignInDTO = new UserSignInDTO();
        userSignInDTO.setUsername("test");
        userSignInDTO.setPassword("password");

        AuthUser user = new AuthUser();
        user.setUsername("test");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(authUserRepository.findByUsername("test")).thenReturn(Optional.of(user));
        when(jwtUtil.generateAccessToken(any(AuthUser.class))).thenReturn("accessToken");
        when(jwtUtil.generateRefreshToken(any(), any(AuthUser.class))).thenReturn("refreshToken");

        AuthDTO response = authService.signIn(userSignInDTO);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("Successfully signed in.");
        assertThat(response.getAccessToken()).isEqualTo("accessToken");
        assertThat(response.getRefreshToken()).isEqualTo("refreshToken");

        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(authUserRepository, times(1)).findByUsername("test");
    }

    @Test
    void testRefreshTokenReturnsNewTokens() {
        RefreshDTO refreshDTO = new RefreshDTO();
        refreshDTO.setRefreshToken("refreshToken");

        AuthUser user = new AuthUser();
        user.setUsername("test");

        when(jwtUtil.getUsernameFromToken(anyString())).thenReturn("test");
        when(authUserRepository.findByUsername("test")).thenReturn(Optional.of(user));
        when(jwtUtil.isTokenValid(anyString(), any(AuthUser.class))).thenReturn(true);
        when(jwtUtil.generateAccessToken(any(AuthUser.class))).thenReturn("updatedAccessToken");
        when(jwtUtil.generateRefreshToken(any(), any(AuthUser.class))).thenReturn("updatedRefreshToken");

        AuthDTO response = authService.refreshToken(refreshDTO);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("Successfully refreshed token.");
        assertThat(response.getAccessToken()).isEqualTo("updatedAccessToken");
        assertThat(response.getRefreshToken()).isEqualTo("updatedRefreshToken");

        verify(jwtUtil, times(1)).getUsernameFromToken(anyString());
        verify(authUserRepository, times(1)).findByUsername("test");
        verify(jwtUtil, times(1)).isTokenValid(anyString(), any(AuthUser.class));
    }

}
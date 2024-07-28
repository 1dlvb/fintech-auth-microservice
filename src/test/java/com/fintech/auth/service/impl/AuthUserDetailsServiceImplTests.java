package com.fintech.auth.service.impl;

import com.fintech.auth.model.AuthUser;
import com.fintech.auth.repository.AuthUserRepository;
import com.onedlvb.advice.LogLevel;
import com.onedlvb.advice.annotation.AuditLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthUserDetailsServiceImplTests {

    @Mock
    private AuthUserRepository authUserRepository;

    @InjectMocks
    private AuthUserDetailsServiceImpl authUserDetailsService;

    @BeforeEach
    void setUp() {
        authUserDetailsService = new AuthUserDetailsServiceImpl(authUserRepository);
    }

    @Test
    void testLoadUserByUsernameUserExists() {
        AuthUser user = new AuthUser();
        user.setUsername("test");

        when(authUserRepository.findByUsername("test")).thenReturn(Optional.of(user));

        UserDetails userDetails = authUserDetailsService.loadUserByUsername("test");

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo("test");

        verify(authUserRepository, times(1)).findByUsername("test");
    }

    @Test
    void testLoadUserByUsernameUserDoesNotExist() {
        when(authUserRepository.findByUsername("test")).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> authUserDetailsService.loadUserByUsername("test"));
        verify(authUserRepository, times(1)).findByUsername("test");
    }

}
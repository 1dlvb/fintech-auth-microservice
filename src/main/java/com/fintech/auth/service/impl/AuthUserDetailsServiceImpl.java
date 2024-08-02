package com.fintech.auth.service.impl;

import com.fintech.auth.repository.AuthUserRepository;
import com.onedlvb.advice.LogLevel;
import com.onedlvb.advice.annotation.AuditLog;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link UserDetailsService} interface for loading user-specific data.
 * @author Matushkin Anton
 */
@Service
@RequiredArgsConstructor
public class AuthUserDetailsServiceImpl implements UserDetailsService {

    @NonNull
    private final AuthUserRepository authUserRepository;


    /**
     * Loads the user by their username.
     * @param username The username identifying the user whose data is required.
     * @return A fully populated user record (never null).
     * @throws UsernameNotFoundException If the user could not be found or the user has no GrantedAuthority.
     */
    @Override
    @AuditLog(logLevel = LogLevel.INFO)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authUserRepository.findByUsername(username).orElseThrow();
    }

}

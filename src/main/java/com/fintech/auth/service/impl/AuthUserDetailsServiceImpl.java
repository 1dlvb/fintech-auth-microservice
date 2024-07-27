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

@Service
@RequiredArgsConstructor
public class AuthUserDetailsServiceImpl implements UserDetailsService {

    @NonNull
    private final AuthUserRepository authUserRepository;

    @Override
    @AuditLog(logLevel = LogLevel.INFO)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authUserRepository.findByUsername(username).orElseThrow();
    }

}

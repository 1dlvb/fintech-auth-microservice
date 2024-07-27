package com.fintech.auth.controller;

import com.fintech.auth.dto.AuthDTO;
import com.fintech.auth.dto.RefreshDTO;
import com.fintech.auth.dto.UserSignInDTO;
import com.fintech.auth.dto.UserSignUpDTO;
import com.fintech.auth.service.AuthService;
import com.onedlvb.advice.LogLevel;
import com.onedlvb.advice.annotation.AuditLogHttp;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @NonNull
    private final AuthService authService;

    @PostMapping("/signup")
    @AuditLogHttp(logLevel = LogLevel.INFO)
    public ResponseEntity<AuthDTO> signup(@RequestBody UserSignUpDTO signupRequest) {
        return ResponseEntity.ok(authService.signup(signupRequest));
    }

    @PostMapping("/signin")
    @AuditLogHttp(logLevel = LogLevel.INFO)
    public ResponseEntity<AuthDTO> signIn(@RequestBody UserSignInDTO signInRequest) {
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }

    @PostMapping("/refresh-token")
    @AuditLogHttp(logLevel = LogLevel.INFO)
    public ResponseEntity<AuthDTO> refreshToken(@RequestBody RefreshDTO refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

}

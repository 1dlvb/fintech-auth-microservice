package com.fintech.auth.controller;

import com.fintech.auth.dto.AuthDTO;
import com.fintech.auth.dto.RefreshDTO;
import com.fintech.auth.dto.UserSignInDTO;
import com.fintech.auth.dto.UserSignUpDTO;
import com.fintech.auth.service.AuthService;
import com.onedlvb.advice.LogLevel;
import com.onedlvb.advice.annotation.AuditLogHttp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController handles authentication-related HTTP requests.
 * @author Matushkin Anton
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Auth Controller", description = "API for auth related requests")
public class AuthController {

    @NonNull
    private final AuthService authService;

    /**
     * Handles user signup requests.
     * @param signupRequest the signup request data
     * @return a ResponseEntity containing the authentication details
     */
    @Operation(summary = "Sign up a new user", description = "Creates a new user account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully signed up",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/signup")
    @AuditLogHttp(logLevel = LogLevel.INFO)
    public ResponseEntity<AuthDTO> signup(@RequestBody UserSignUpDTO signupRequest) {
        return ResponseEntity.ok(authService.signup(signupRequest));
    }

    /**
     * Handles user signin requests.
     * @param signInRequest the signin request data
     * @return a ResponseEntity containing the authentication details
     */
    @Operation(summary = "Sign in a user", description = "Authenticates a user and returns authentication details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully signed in",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/signin")
    @AuditLogHttp(logLevel = LogLevel.INFO)
    public ResponseEntity<AuthDTO> signIn(@RequestBody UserSignInDTO signInRequest) {
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }

    /**
     * Handles token refresh requests.
     * @param refreshTokenRequest the refresh token request data
     * @return a ResponseEntity containing the new authentication details
     */
    @Operation(summary = "Refresh authentication token",
            description = "Refreshes an existing authentication token and returns new authentication details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully refreshed token",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid refresh token", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/refresh-token")
    @AuditLogHttp(logLevel = LogLevel.INFO)
    public ResponseEntity<AuthDTO> refreshToken(@RequestBody RefreshDTO refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

}

package com.fintech.auth.service;

import com.fintech.auth.dto.AuthDTO;
import com.fintech.auth.dto.RefreshDTO;
import com.fintech.auth.dto.UserSignInDTO;
import com.fintech.auth.dto.UserSignUpDTO;

/**
 * Service interface for handling authentication-related operations.
 * @author Matushkin Anton
 */
public interface AuthService {

    /**
     * Registers a new user and returns an authDTO.
     * @param registrationRequest The DTO containing the user's registration details.
     * @return An {@link AuthDTO}.
     */
    AuthDTO signup(UserSignUpDTO registrationRequest);

    /**
     * Authenticates a user and returns an authDTO containing tokens and status information.
     * @param requestAuthDTO The DTO containing the user's sign-in details.
     * @return An {@link AuthDTO} containing the status code, access token, refresh token, and message.
     */
    AuthDTO signIn(UserSignInDTO requestAuthDTO);

    /**
     * Refreshes the access token using the provided refresh token and returns an authentication DTO containing tokens and status information.
s     * @param refreshTokenRequest The DTO containing the refresh token.
     * @return An {@link AuthDTO} containing the status code, new access token, refresh token, and message.
     */
    AuthDTO refreshToken(RefreshDTO refreshTokenRequest);

}

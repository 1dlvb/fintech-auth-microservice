package com.fintech.auth.service;

import com.fintech.auth.dto.AuthDTO;
import com.fintech.auth.dto.RefreshDTO;
import com.fintech.auth.dto.UserSignInDTO;
import com.fintech.auth.dto.UserSignUpDTO;

public interface AuthService {

    AuthDTO signup(UserSignUpDTO registrationRequest);

    AuthDTO signIn(UserSignInDTO requestAuthDTO);

    AuthDTO refreshToken(RefreshDTO refreshTokenRequest);

}

package com.fintech.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.auth.config.ServiceConfig;
import com.fintech.auth.config.auth.SecurityConfig;
import com.fintech.auth.dto.AuthDTO;
import com.fintech.auth.dto.RefreshDTO;
import com.fintech.auth.dto.UserSignInDTO;
import com.fintech.auth.dto.UserSignUpDTO;
import com.fintech.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import({ServiceConfig.class, SecurityConfig.class})
class AuthControllerTests {
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test_db")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    public static void setTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testSignUpCreatesUser() throws Exception {
        AuthDTO authDTO = getSignUpAuthDTO();
        UserSignUpDTO signUpDTO = getSignUpDTO();

        when(authService.signup(signUpDTO)).thenReturn(authDTO);

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(authDTO)));
    }


    @Test
    public void testSignInReturnsProperResponse() throws Exception {
        UserSignInDTO signInDTO = new UserSignInDTO();
        signInDTO.setUsername("test");
        signInDTO.setPassword("test password");

        AuthDTO authDTO = new AuthDTO();
        authDTO.setStatusCode(200);
        authDTO.setMessage("Successfully signed in.");
        authDTO.setAccessToken("access token");
        authDTO.setRefreshToken("refresh token");
        authDTO.setExpirationTimeMils("180000");


        when(authService.signIn(signInDTO)).thenReturn(authDTO);

        mockMvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status_code").value(200))
                .andExpect(jsonPath("$.message").value("Successfully signed in."))
                .andExpect(jsonPath("$.access_token").value("access token"))
                .andExpect(jsonPath("$.refresh_token").value("refresh token"))
                .andExpect(jsonPath("$.expiration_time").value("180000"));
    }

    @Test
    public void testRefreshTokenReturnsNewToken() throws Exception {
        RefreshDTO refreshTokenRequest = new RefreshDTO();
        refreshTokenRequest.setRefreshToken("refresh token");
        AuthDTO authDTO = new AuthDTO();
        authDTO.setStatusCode(200);
        authDTO.setMessage("Successfully refreshed token.");
        authDTO.setAccessToken("access token");
        authDTO.setRefreshToken("refresh token");
        authDTO.setExpirationTimeMils("180000");
        when(authService.refreshToken(refreshTokenRequest)).thenReturn(authDTO);

        mockMvc.perform(post("/auth/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(refreshTokenRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status_code").value(200))
                .andExpect(jsonPath("$.message").value("Successfully refreshed token."))
                .andExpect(jsonPath("$.access_token").value("access token"))
                .andExpect(jsonPath("$.refresh_token").value("refresh token"))
                .andExpect(jsonPath("$.expiration_time").value("180000"));
    }

    private AuthDTO getSignUpAuthDTO() {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setStatusCode(200);
        authDTO.setMessage("User saved successfully.");
        return authDTO;
    }

    private UserSignUpDTO getSignUpDTO() {
        UserSignUpDTO signUpDTO = new UserSignUpDTO();
        signUpDTO.setUsername("test");
        signUpDTO.setEmail("test@test.xyz");
        signUpDTO.setPassword("test password");
        return signUpDTO;
    }

}
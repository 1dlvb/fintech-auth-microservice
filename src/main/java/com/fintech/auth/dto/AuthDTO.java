package com.fintech.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AuthDTO is a DTO representing authentication response details.
 * @author Matushkin Anton
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"status_code", "error", "message", "access_token", "refresh_token"})
public class AuthDTO {

    @JsonProperty("status_code")
    @Schema(description = "HTTP status code of the authentication response", example = "200")
    private int statusCode;

    @JsonProperty("error")
    @Schema(description = "Error message related to the authentication process",
            example = "Invalid credentials")
    private String error;

    @JsonProperty("message")
    @Schema(description = "Additional information about the authentication process",
            example = "Authentication successful")
    private String message;

    @JsonProperty("access_token")
    @Schema(description = "JWT access token issued upon successful authentication",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @JsonProperty("refresh_token")
    @Schema(description = "JWT refresh token issued upon successful authentication",
            example = "dGVzdF9yZWZyZXNoX3Rva2Vu...")
    private String refreshToken;

    @JsonProperty("expiration_time")
    @Schema(description = "Expiration time of the access token in milliseconds", example = "10000")
    private String expirationTimeMils;

}

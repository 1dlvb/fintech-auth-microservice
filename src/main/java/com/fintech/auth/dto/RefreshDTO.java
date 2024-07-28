package com.fintech.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RefreshDTO is a DTO representing a request to refresh an authentication token.
 * @author Matushkin Anton
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"refresh_token"})
public class RefreshDTO {

    @JsonProperty("refresh_token")
    @Schema(description = "The refresh token used to obtain a new access token",
            example = "dGVzdF9yZWZyZXNoX3Rva2Vu...")
    private String refreshToken;

}

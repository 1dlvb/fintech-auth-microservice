package com.fintech.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserSignInDTO is a DTO representing a user's sign-in request.
 * @author Matushkin Anton
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"username", "password"})
public class UserSignInDTO {

    @JsonProperty("username")
    @Schema(description = "The username of the user attempting to sign in", example = "john_doe")
    private String username;

    @JsonProperty("password")
    @Schema(description = "The password of the user attempting to sign in", example = "password")
    private String password;

}

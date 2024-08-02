package com.fintech.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserSignUpDTO is a data transfer object representing a user's sign-up request.
 * @author Matushkin Anton
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"username", "email", "password"})
public class UserSignUpDTO {

    @JsonProperty("username")
    @Schema(description = "The username of the user attempting to sign up", example = "john_doe")
    private String username;

    @JsonProperty("email")
    @Schema(description = "The email address of the user attempting to sign up", example = "john_doe@example.com")
    private String email;

    @JsonProperty("password")
    @Schema(description = "The password of the user attempting to sign up", example = "password")
    private String password;

}

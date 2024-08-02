package com.fintech.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * UserWithRolesDTO is a data transfer object representing a user with assigned roles.
 * @author Matushkin Anton
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"username", "roles"})
public class UserWithRolesDTO {

    @JsonProperty("username")
    @Schema(description = "The username of the user", example = "john_doe")
    private String username;

    @JsonProperty("roles")
    @Schema(description = "The set of role IDs assigned to the user", example = "[\"ADMIN\", \"USER\"]")
    private Set<String> roleIds;

}


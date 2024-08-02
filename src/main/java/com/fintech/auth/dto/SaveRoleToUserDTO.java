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
 * AuthDTO is a data transfer object representing authentication response details.
 * @author Matushkin Anton
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"username", "roles"})
public class SaveRoleToUserDTO {

    @JsonProperty("username")
    @Schema(description = "The username of the user to whom roles are to be assigned", example = "john_doe")
    private String username;

    @JsonProperty("roles")
    @Schema(description = "A set of role IDs to be assigned to the user", example = "[\"ADMIN\", \"USER\"]")
    private Set<String> roleIds;

}

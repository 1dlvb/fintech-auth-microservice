package com.fintech.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"username", "roles"})
public class UserWithRolesDTO {

    @JsonProperty("username")
    private String username;

    @JsonProperty("roles")
    private Set<String> roleIds;

}


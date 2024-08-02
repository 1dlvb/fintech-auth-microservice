package com.fintech.auth.controller;

import com.fintech.auth.dto.UserWithRolesDTO;
import com.fintech.auth.service.RoleService;
import com.onedlvb.advice.LogLevel;
import com.onedlvb.advice.annotation.AuditLogHttp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserRolesController handles role retrieval-related HTTP requests.
 * @author Matushkin Anton
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user-roles")
@Tag(name = "RolesEnum Controller", description = "API for getting info about user roles")
public class UserRolesController {

    @NonNull
    private final RoleService roleService;

    /**
     * Retrieves roles assigned to the user with the specified login.
     * @return the user details along with assigned roles
     */
    @Operation(summary = "Get user roles",
            description = "Retrieves the roles assigned to current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user roles",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserWithRolesDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping()
    @AuditLogHttp(logLevel = LogLevel.INFO)
    public UserWithRolesDTO getRoles(@AuthenticationPrincipal UserDetails userDetails) {
        return roleService.getUserWithRolesByUsername(userDetails.getUsername());
    }

    /**
     * Retrieves roles assigned to the user with the specified login.
     * @param login the login of the user
     * @return the user details along with assigned roles
     */
    @Operation(summary = "Get user roles by login",
            description = "Retrieves the roles assigned to a user identified by the login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user roles",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserWithRolesDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{login}")
    @AuditLogHttp(logLevel = LogLevel.INFO)
    public UserWithRolesDTO getRolesByLogin(@PathVariable String login) {
        return roleService.getUserWithRolesByUsername(login);
    }

}





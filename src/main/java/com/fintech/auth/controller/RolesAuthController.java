package com.fintech.auth.controller;

import com.fintech.auth.dto.SaveRoleToUserDTO;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RolesAuthController handles role management-related HTTP requests.
 * @author Matushkin Anton
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
@Tag(name = "Roles Controller", description = "API for handling roles")
public class RolesAuthController {

    @NonNull
    private final RoleService roleService;

    /**
     * Assigns a role to a user.
     *
     * @param saveRoleToUserRequest the request containing user and role information
     * @return the updated user with roles
     */
    @Operation(summary = "Assign a role to a user", description = "Assigns a specified role to a user and returns the updated user details with roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully assigned role to user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserWithRolesDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/save")
    @AuditLogHttp(logLevel = LogLevel.INFO)
    public UserWithRolesDTO saveRoleToUser(@RequestBody SaveRoleToUserDTO saveRoleToUserRequest) {
        return roleService.saveRoleToUser(saveRoleToUserRequest);
    }

}





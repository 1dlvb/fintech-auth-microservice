package com.fintech.auth.service;

import com.fintech.auth.dto.SaveRoleToUserDTO;
import com.fintech.auth.dto.UserWithRolesDTO;

/**
 * Service interface for handling role-related operations.
 * @author Matushkin Anton
 */
public interface RoleService {

    /**
     * Assigns roles to a user and returns the updated user with roles.
     * @param saveRoleToUserDTO The DTO containing the user's username and the roles to be assigned.
     * @return A {@link UserWithRolesDTO} containing the user's username and assigned roles.
     */
    UserWithRolesDTO saveRoleToUser(SaveRoleToUserDTO saveRoleToUserDTO);

    /**
     * Retrieves a user with roles based on their username.
     * @param login The username of the user.
     * @return A {@link UserWithRolesDTO} containing the user's username and roles.
     */
    UserWithRolesDTO getUserWithRolesByUsername(String login);

}

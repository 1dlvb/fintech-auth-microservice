package com.fintech.auth.service;

import com.fintech.auth.dto.SaveRoleToUserDTO;
import com.fintech.auth.dto.UserWithRolesDTO;

public interface RoleService {

    UserWithRolesDTO saveRoleToUser(SaveRoleToUserDTO saveRoleToUserDTO);

    UserWithRolesDTO getUserWithRolesByUsername(String login);

}

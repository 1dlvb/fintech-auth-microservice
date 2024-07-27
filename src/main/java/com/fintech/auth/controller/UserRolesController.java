package com.fintech.auth.controller;

import com.fintech.auth.dto.UserWithRolesDTO;
import com.fintech.auth.service.RoleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-roles")
@RequiredArgsConstructor
public class UserRolesController {

    @NonNull
    private final RoleService roleService;

    @GetMapping("/{login}")
    public UserWithRolesDTO getRolesByLogin(@PathVariable String login) {
        return roleService.getUserWithRolesByUsername(login);
    }

}





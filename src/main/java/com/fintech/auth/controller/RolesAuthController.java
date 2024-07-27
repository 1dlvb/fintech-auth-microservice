package com.fintech.auth.controller;

import com.fintech.auth.dto.SaveRoleToUserDTO;
import com.fintech.auth.dto.UserWithRolesDTO;
import com.fintech.auth.service.RoleService;
import com.onedlvb.advice.LogLevel;
import com.onedlvb.advice.annotation.AuditLogHttp;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RolesAuthController {

    @NonNull
    private final RoleService roleService;

    @PutMapping("/save")
    @AuditLogHttp(logLevel = LogLevel.INFO)
    public UserWithRolesDTO saveRoleToUser(@RequestBody SaveRoleToUserDTO saveRoleToUserRequest) {
        return roleService.saveRoleToUser(saveRoleToUserRequest);
    }

}





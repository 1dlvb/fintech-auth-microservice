package com.fintech.auth.service.impl;

import com.fintech.auth.dto.SaveRoleToUserDTO;
import com.fintech.auth.dto.UserWithRolesDTO;
import com.fintech.auth.model.AuthUser;
import com.fintech.auth.model.Role;
import com.fintech.auth.repository.AuthUserRepository;
import com.fintech.auth.repository.RoleRepository;
import com.fintech.auth.service.RoleService;
import com.onedlvb.advice.LogLevel;
import com.onedlvb.advice.annotation.AuditLog;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    @NonNull
    private final AuthUserRepository userRepository;

    @NonNull
    private final RoleRepository roleRepository;

    @Override
    @AuditLog(logLevel = LogLevel.INFO)
    public UserWithRolesDTO saveRoleToUser(SaveRoleToUserDTO saveRoleToUserDTO) {
        AuthUser user = userRepository.findByUsername(saveRoleToUserDTO.getUsername()).orElseThrow();

        Set<Role> roles = saveRoleToUserDTO.getRoleIds().stream()
                .map(roleId -> roleRepository.findById(roleId)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleId)))
                .collect(Collectors.toSet());
        user.setRoles(roles);
        userRepository.save(user);
        return UserWithRolesDTO.builder()
                .username(saveRoleToUserDTO.getUsername())
                .roleIds(saveRoleToUserDTO.getRoleIds())
                .build();
    }

    @Override
    @AuditLog(logLevel = LogLevel.INFO)
    public UserWithRolesDTO getUserWithRolesByUsername(String login) {
        AuthUser user = userRepository.findByUsername(login).orElseThrow();
        return UserWithRolesDTO.builder()
                .username(user.getUsername())
                .roleIds(user.getRoles().stream().map(Role::getId).collect(Collectors.toSet()))
                .build();
    }

}

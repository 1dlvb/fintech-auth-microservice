package com.fintech.auth.service.impl;

import com.fintech.auth.dto.SaveRoleToUserDTO;
import com.fintech.auth.dto.UserWithRolesDTO;
import com.fintech.auth.model.AuthUser;
import com.fintech.auth.model.Role;
import com.fintech.auth.repository.AuthUserRepository;
import com.fintech.auth.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTests {

    @Mock
    private AuthUserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    void setUp() {
        roleService = new RoleServiceImpl(userRepository, roleRepository);
    }

    @Test
    void testSaveRoleToUserUserExistsAndRolesExist() {
        AuthUser user = new AuthUser();
        user.setUsername("test");

        Role role = new Role();
        role.setId("TEST ROLE");

        SaveRoleToUserDTO saveRoleToUserDTO = new SaveRoleToUserDTO();
        saveRoleToUserDTO.setUsername("test");
        saveRoleToUserDTO.setRoleIds(Set.of("TEST ROLE"));

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        when(roleRepository.findById("TEST ROLE")).thenReturn(Optional.of(role));

        UserWithRolesDTO userWithRolesDTO = roleService.saveRoleToUser(saveRoleToUserDTO);

        assertThat(userWithRolesDTO).isNotNull();
        assertThat(userWithRolesDTO.getUsername()).isEqualTo("test");

        verify(userRepository, times(1)).findByUsername("test");
        verify(roleRepository, times(1)).findById("TEST ROLE");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testSaveRoleToUserUserDoesNotExist() {
        SaveRoleToUserDTO saveRoleToUserDTO = new SaveRoleToUserDTO();
        saveRoleToUserDTO.setUsername("test");
        saveRoleToUserDTO.setRoleIds(Set.of("TEST ROLE"));

        when(userRepository.findByUsername("test")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> roleService.saveRoleToUser(saveRoleToUserDTO));

        verify(userRepository, times(1)).findByUsername("test");
        verify(roleRepository, never()).findById(anyString());
        verify(userRepository, never()).save(any(AuthUser.class));
    }

    @Test
    void testSaveRoleToUserRoleDoesNotExist() {
        AuthUser user = new AuthUser();
        user.setUsername("test");

        SaveRoleToUserDTO saveRoleToUserDTO = new SaveRoleToUserDTO();
        saveRoleToUserDTO.setUsername("test");
        saveRoleToUserDTO.setRoleIds(Set.of("TEST ROLE"));

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        when(roleRepository.findById("TEST ROLE")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> roleService.saveRoleToUser(saveRoleToUserDTO));

        verify(userRepository, times(1)).findByUsername("test");
        verify(roleRepository, times(1)).findById("TEST ROLE");
        verify(userRepository, never()).save(any(AuthUser.class));
    }

    @Test
    void testGetUserWithRolesByUsernameUserExists() {
        AuthUser user = new AuthUser();
        user.setUsername("test");

        Role role = new Role();
        role.setId("TEST ROLE");
        user.setRoles(Set.of(role));

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));

        UserWithRolesDTO userWithRolesDTO = roleService.getUserWithRolesByUsername("test");

        assertThat(userWithRolesDTO).isNotNull();
        assertThat(userWithRolesDTO.getUsername()).isEqualTo("test");

        verify(userRepository, times(1)).findByUsername("test");
    }

    @Test
    void testGetUserWithRolesByUsernameUserDoesNotExist() {
        when(userRepository.findByUsername("test")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> roleService.getUserWithRolesByUsername("test"));

        verify(userRepository, times(1)).findByUsername("test");
    }

}
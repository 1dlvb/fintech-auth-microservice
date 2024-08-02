package com.fintech.auth.repository;

import com.fintech.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A repository for roles
 * @author Matushkin Anton
 */
public interface RoleRepository  extends JpaRepository<Role, String> {
}

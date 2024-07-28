package com.fintech.auth.repository;

import com.fintech.auth.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A repository for auth
 * @author Matushkin Anton
 */
@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, String> {

    Optional<AuthUser> findByUsername(String s);

    Optional<AuthUser> findByEmail(String s);

}

package com.fintech.auth.repository;

import com.fintech.auth.config.ServiceConfig;
import com.fintech.auth.model.AuthUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Import({ServiceConfig.class})
class AuthUserRepositoryTests {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test_db")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    public static void setTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private AuthUserRepository authUserRepository;

    @Test
    void testFindByUsernameReturnsUser() {
        AuthUser user = new AuthUser();
        user.setUsername("test user");
        user.setEmail("testUser@example.com");
        user.setPassword("test password");
        authUserRepository.save(user);

        Optional<AuthUser> foundUser = authUserRepository.findByUsername("test user");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("test user");
    }

    @Test
    void testFindByEmailReturnsUser() {
        AuthUser user = new AuthUser();
        user.setUsername("test user");
        user.setEmail("testUser@example.com");
        user.setPassword("test password");
        authUserRepository.save(user);

        Optional<AuthUser> foundUser = authUserRepository.findByEmail("testUser@example.com");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("testUser@example.com");
    }

    @Test
    void testFindByUsernameNotFoundReturnsNonPresent() {
        Optional<AuthUser> foundUser = authUserRepository.findByUsername("non-existent user");
        assertThat(foundUser).isNotPresent();
    }

    @Test
    void testFindByEmailNotFoundReturnsNonPresent() {
        Optional<AuthUser> foundUser = authUserRepository.findByEmail("nonexistent@test.zxc");
        assertThat(foundUser).isNotPresent();
    }

}

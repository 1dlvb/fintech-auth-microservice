package com.fintech.auth.config;

import com.fintech.auth.auditor.AuditorAwareImpl;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration file for spring boot application
 * @author Matushkin Anton
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class ServiceConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Auth API")
                        .description("An API for serving authenticating operations.")
                        .version("0.0.1-SNAPSHOT")
                );
    }

}

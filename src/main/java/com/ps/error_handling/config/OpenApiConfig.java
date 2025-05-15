package com.ps.error_handling.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

/**
 * Configuration class for OpenAPI documentation. This class configures the OpenAPI documentation for the application.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Creates and configures the OpenAPI documentation.
     *
     * @return the configured OpenAPI instance
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Error Handling API")
                        .description("API for demonstrating error handling in Spring Boot applications")
                        .version("1.0.0")
                        .contact(new Contact().name("Pack Solutions").email("admin@pack-solutions.com")));
    }
}

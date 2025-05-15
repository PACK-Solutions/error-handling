package com.ps.error_handling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * Main application class for the Error Handling application. This application demonstrates various error handling
 * techniques in a Spring Boot application.
 */
@SpringBootApplication
public class ErrorHandlingApplication {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandlingApplication.class);

    /**
     * Main method to start the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ErrorHandlingApplication.class, args);
    }

    /**
     * ApplicationRunner bean that logs the SwaggerUI URL on startup.
     *
     * @param env the Spring environment
     * @return an ApplicationRunner that logs the SwaggerUI URL
     */
    @Bean
    public ApplicationRunner logOpenApiDocsUrl(Environment env) {
        return args -> {
            String port = env.getProperty("server.port", "8080");
            String contextPath = env.getProperty("server.servlet.context-path", "");
            logger.info("SwaggerUI available at: http://localhost:{}{}/swagger-ui.html", port, contextPath);
        };
    }
}

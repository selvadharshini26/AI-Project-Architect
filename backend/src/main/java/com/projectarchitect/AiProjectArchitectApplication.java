package com.projectarchitect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the AI Project Architect backend application.
 * Base package for component scanning — all sub-packages
 * (controller, service, repository, config, security, etc.)
 * are picked up automatically.
 */
@SpringBootApplication
public class AiProjectArchitectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiProjectArchitectApplication.class, args);
    }
}

package com.projectarchitect.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Binds Google Gemini API configuration properties from application.yml
 * (prefix: {@code app.gemini}) and exposes shared beans required to
 * communicate with the Gemini REST API.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "app.gemini")
public class GeminiConfig {

    /**
     * The Gemini API key, injected from environment/config (see application.yml).
     */
    private String apiKey;

    /**
     * Base URL of the Gemini generative language API.
     */
    private String baseUrl = "https://generativelanguage.googleapis.com/v1beta/models";

    /**
     * The Gemini model to use for generation, e.g. "gemini-1.5-flash".
     */
    private String model = "gemini-1.5-flash";

    /**
     * Request timeout in milliseconds.
     */
    private int timeoutMs = 60000;

    @Bean
    public RestTemplate geminiRestTemplate() {
        return new RestTemplate();
    }
}

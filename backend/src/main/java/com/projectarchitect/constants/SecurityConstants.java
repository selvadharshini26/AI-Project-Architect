package com.projectarchitect.constants;

/**
 * Constants related to Spring Security and JWT configuration.
 */
public final class SecurityConstants {

    private SecurityConstants() {
        // Prevent instantiation
    }

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String[] PUBLIC_ENDPOINTS = {
            "/api/auth/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**"
    };
}

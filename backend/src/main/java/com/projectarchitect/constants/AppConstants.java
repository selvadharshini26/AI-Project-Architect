package com.projectarchitect.constants;

/**
 * Application-wide constants used across the AI Project Architect backend.
 */
public final class AppConstants {

    private AppConstants() {
        // Prevent instantiation
    }

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    public static final String DEFAULT_SORT_FIELD = "createdAt";

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";
}

package com.projectarchitect.exception;

/**
 * Thrown when the integration with the Google Gemini AI service fails,
 * e.g. network errors, malformed responses, or quota issues.
 */
public class AiServiceException extends RuntimeException {

    public AiServiceException(String message) {
        super(message);
    }

    public AiServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

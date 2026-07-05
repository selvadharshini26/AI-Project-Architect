package com.projectarchitect.exception;

/**
 * Thrown when a request fails authentication or the caller does not
 * hold the required permissions to perform an operation.
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}

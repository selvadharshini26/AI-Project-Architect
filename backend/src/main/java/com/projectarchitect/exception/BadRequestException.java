package com.projectarchitect.exception;

/**
 * Thrown when a client request is malformed or violates a business rule,
 * e.g. attempting to register with an already-used email address.
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}

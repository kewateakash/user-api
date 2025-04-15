package com.example.exception;

/**
 * Exception thrown when the user's age is below the minimum allowed.
 */
public class UserUnderageException extends RuntimeException {
    public UserUnderageException(String message) {
        super(message);
    }
}


package com.example.exception;

/**
 * Exception thrown when the user's country is not allowed.
 */
public class UserCountryNotAllowedException extends RuntimeException {
    public UserCountryNotAllowedException(String message) {
        super(message);
    }
}


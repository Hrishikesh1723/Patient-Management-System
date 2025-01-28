package com.example.PatientManagementSystem.exception;

/**
 * Custom exception for handling API request errors.
 * This exception can be used to signal errors related to invalid API requests
 * or issues with input validation, and it allows chaining the root cause.
 */
public class ApiRequestException extends RuntimeException {

    /**
     * Constructs a new ApiRequestException with the specified detail message.
     *
     * @param message the detail message.
     */
    public ApiRequestException(String message) {
        super(message);
    }

    /**
     * Constructs a new ApiRequestException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception (can be null).
     */
    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}

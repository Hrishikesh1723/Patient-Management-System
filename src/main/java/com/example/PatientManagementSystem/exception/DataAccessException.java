package com.example.PatientManagementSystem.exception;

/**
 * Custom exception class for handling data access errors in the application.
 * This exception is used to represent issues that occur while interacting with the
 * data layer (e.g., database access errors). It extends the {@link RuntimeException},
 * allowing it to be thrown without being explicitly declared in method signatures.
 */
public class DataAccessException extends RuntimeException {

    /**
     * Constructs a new DataAccessException with the specified detail message.
     *
     * @param message the detail message, providing more information about the error.
     */
    public DataAccessException(String message) {
        super(message);
    }

    /**
     * Constructs a new DataAccessException with the specified detail message
     * and cause.
     *
     * @param message the detail message, providing more information about the error.
     * @param cause   the cause of the exception (can be null).
     */
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}

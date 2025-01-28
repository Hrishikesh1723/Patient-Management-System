package com.example.PatientManagementSystem.exception;

/**
 * Custom exception class for handling service layer errors in the application.
 * This exception is used to represent issues that occur within the service layer,
 * such as business logic errors or problems interacting with other layers of the
 * application. It extends {@link RuntimeException}, allowing it to be used without
 * being explicitly declared in method signatures.
 */
public class ServiceException extends RuntimeException {

    /**
     * Constructs a new ServiceException with the specified detail message.
     *
     * @param message the detail message, providing more information about the error.
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new ServiceException with the specified detail message and cause.
     *
     * @param message the detail message, providing more information about the error.
     * @param cause   the cause of the exception (can be null).
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

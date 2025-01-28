package com.example.PatientManagementSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the Patient Management System.
 * This class provides centralized exception handling for all exceptions
 * thrown by the application, ensuring consistent error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles exceptions specific to API requests (controller layer).
     *
     * @param ex the exception to handle.
     * @return a ResponseEntity containing an ApiError object and an HTTP BAD_REQUEST status.
     */
    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<ApiError> handleApiRequestException(ApiRequestException ex) {
        logger.error("Controller Exception: {}", ex.getMessage());
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    /**
     * Handles exceptions specific to the service layer.
     *
     * @param ex the exception to handle.
     * @return a ResponseEntity containing an ApiError object and an HTTP INTERNAL_SERVER_ERROR status.
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiError> handleServiceException(ServiceException ex) {
        logger.error("Service Exception: {}", ex.getMessage());
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    /**
     * Handles exceptions specific to the data access layer (DAO).
     *
     * @param ex the exception to handle.
     * @return a ResponseEntity containing an ApiError object and an HTTP INTERNAL_SERVER_ERROR status.
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiError> handleDataAccessException(DataAccessException ex) {
        logger.error("DAO Exception: {}", ex.getMessage());
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    /**
     * Handles all unhandled or unexpected exceptions.
     *
     * @param ex the exception to handle.
     * @return a ResponseEntity containing a generic ApiError object and an HTTP INTERNAL_SERVER_ERROR status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex) {
        logger.error("Unhandled Exception: {}", ex.getMessage());
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred."));
    }

    /**
     * Builds a ResponseEntity object for a given ApiError.
     *
     * @param apiError the ApiError object to include in the response.
     * @return a ResponseEntity containing the ApiError object and its associated HTTP status.
     */
    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}

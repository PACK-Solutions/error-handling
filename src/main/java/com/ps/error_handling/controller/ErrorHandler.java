package com.ps.error_handling.controller;

import static org.springframework.http.HttpStatus.*;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ps.error_handling.service.exception.*;

import jakarta.validation.ConstraintViolationException;

/**
 * Global exception handler for the application. This class handles exceptions thrown by the application and converts
 * them into appropriate HTTP responses. It uses the {@link RestControllerAdvice} annotation to intercept exceptions
 * thrown by controllers and provides centralized exception handling across all controllers.
 */
@RestControllerAdvice
public class ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    private static final URI DATABASE_ERROR_TYPE = URI.create("https://api.error-handling.com/problems/database-error");
    private static final URI REPORT_CLIENT_ERROR_TYPE =
            URI.create("https://api.error-handling.com/problems/report-client-error");
    private static final URI INVALID_REPORT_NAME_TYPE =
            URI.create("https://api.error-handling.com/problems/invalid-report-name");
    private static final URI USER_NOT_FOUND_TYPE = URI.create("https://api.error-handling.com/problems/user-not-found");
    private static final URI REPORT_NOT_FOUND_TYPE =
            URI.create("https://api.error-handling.com/problems/report-not-found");
    private static final URI VALIDATION_ERROR_TYPE =
            URI.create("https://api.error-handling.com/problems/validation-error");
    private static final URI ILLEGAL_ARGUMENT_TYPE =
            URI.create("https://api.error-handling.com/problems/illegal-argument");

    /**
     * Handles exceptions related to database access errors. This method is called when a
     * {@link DatabaseAccessException} is thrown. It returns a 500 Internal Server Error response with a generic error
     * message.
     *
     * @param ex the exception that was thrown
     * @return a {@link ProblemDetail} with status code 500 and a generic error message
     */
    @ExceptionHandler(DatabaseAccessException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ProblemDetail handleDatabaseAccessException(DatabaseAccessException ex) {
        logger.error("Database access error", ex);
        ProblemDetail problemDetail = ProblemDetail.forStatus(INTERNAL_SERVER_ERROR);
        problemDetail.setType(DATABASE_ERROR_TYPE);
        problemDetail.setDetail("Internal server error");
        return problemDetail;
    }

    /**
     * Handles exceptions related to unexpected errors from the report client. This method is called when a
     * {@link ReportClientUnexpectedException} is thrown. It returns a 500 Internal Server Error response with a generic
     * error message.
     *
     * @param ex the exception that was thrown
     * @return a {@link ProblemDetail} with status code 500 and a generic error message
     */
    @ExceptionHandler(ReportClientUnexpectedException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ProblemDetail handleReportClientUnexpectedException(ReportClientUnexpectedException ex) {
        logger.error("Unexpected error from report client", ex);
        ProblemDetail problemDetail = ProblemDetail.forStatus(INTERNAL_SERVER_ERROR);
        problemDetail.setType(REPORT_CLIENT_ERROR_TYPE);
        problemDetail.setDetail("Internal server error");
        return problemDetail;
    }

    /**
     * Handles exceptions related to invalid report names. This method is called when a
     * {@link ReportNameNotFoundInReportApiException} is thrown. It returns a 400 Bad Request response with an error
     * message indicating the report name is invalid.
     *
     * @param ex the exception that was thrown
     * @return a {@link ProblemDetail} with status code 400 and an error message about invalid report name
     */
    @ExceptionHandler(ReportNameNotFoundInReportApiException.class)
    @ResponseStatus(BAD_REQUEST)
    public ProblemDetail handleReportNameNotFoundInReportApiException(ReportNameNotFoundInReportApiException ex) {
        logger.error("Invalid report name", ex);
        ProblemDetail problemDetail = ProblemDetail.forStatus(BAD_REQUEST);
        problemDetail.setType(INVALID_REPORT_NAME_TYPE);
        problemDetail.setDetail("Invalid Report name.");
        return problemDetail;
    }

    /**
     * Handles exceptions related to users not found in the database. This method is called when a
     * {@link UserNotFoundInDbException} is thrown. It returns a 404 Not Found response with an error message indicating
     * the user ID doesn't exist.
     *
     * @param ex the exception that was thrown
     * @return a {@link ProblemDetail} with status code 404 and an error message about non-existent user ID
     */
    @ExceptionHandler(UserNotFoundInDbException.class)
    @ResponseStatus(NOT_FOUND)
    public ProblemDetail handleUserNotFoundInDbException(UserNotFoundInDbException ex) {
        logger.error("User not found in database", ex);
        ProblemDetail problemDetail = ProblemDetail.forStatus(NOT_FOUND);
        problemDetail.setType(USER_NOT_FOUND_TYPE);
        problemDetail.setDetail("Provided user id doesn't exist.");
        return problemDetail;
    }

    /**
     * Handles exceptions related to users not found in the report API. This method is called when a
     * {@link UserNotFoundInReportApiException} is thrown. It returns a 404 Not Found response with an error message
     * indicating the report for the user was not found.
     *
     * @param ex the exception that was thrown
     * @return a {@link ProblemDetail} with status code 404 and an error message about report not found for user
     */
    @ExceptionHandler(UserNotFoundInReportApiException.class)
    @ResponseStatus(NOT_FOUND)
    public ProblemDetail handleUserNotFoundInReportApiException(UserNotFoundInReportApiException ex) {
        logger.error("Report not found for user", ex);
        ProblemDetail problemDetail = ProblemDetail.forStatus(NOT_FOUND);
        problemDetail.setType(REPORT_NOT_FOUND_TYPE);
        problemDetail.setDetail("Report for given user not found.");
        return problemDetail;
    }

    /**
     * Handles validation constraint violations. This method is called when a {@link ConstraintViolationException} is
     * thrown. It returns a 400 Bad Request response with details about the validation errors.
     *
     * @param ex the exception that was thrown
     * @return a {@link ProblemDetail} with status code 400 and details about the validation errors
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException ex) {
        logger.error("Validation error", ex);
        ProblemDetail problemDetail = ProblemDetail.forStatus(BAD_REQUEST);
        problemDetail.setType(VALIDATION_ERROR_TYPE);
        problemDetail.setDetail("Validation error: " + ex.getMessage());
        return problemDetail;
    }

    /**
     * Handles illegal argument exceptions. This method is called when an {@link IllegalArgumentException} is thrown. It
     * returns a 400 Bad Request response with details about the illegal argument.
     *
     * @param ex the exception that was thrown
     * @return a {@link ProblemDetail} with status code 400 and details about the illegal argument
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("Illegal argument", ex);
        ProblemDetail problemDetail = ProblemDetail.forStatus(BAD_REQUEST);
        problemDetail.setType(ILLEGAL_ARGUMENT_TYPE);
        problemDetail.setDetail("Invalid argument: " + ex.getMessage());
        return problemDetail;
    }
}

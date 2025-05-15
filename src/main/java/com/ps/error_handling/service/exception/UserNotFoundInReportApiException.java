package com.ps.error_handling.service.exception;

/**
 * Exception thrown when a user is not found in the report API. This exception indicates that the requested user does
 * not exist in the external report API.
 */
public class UserNotFoundInReportApiException extends RuntimeException {

    /** Constructs a new UserNotFoundInReportApiException with no detail message. */
    public UserNotFoundInReportApiException() {
        super("User not found in the report API");
    }

    /**
     * Constructs a new UserNotFoundInReportApiException with the specified detail message.
     *
     * @param message the detail message
     */
    public UserNotFoundInReportApiException(String message) {
        super(message);
    }

    /**
     * Constructs a new UserNotFoundInReportApiException with the specified email.
     *
     * @param email the email of the user that was not found
     * @return a new UserNotFoundInReportApiException with a message including the email
     */
    public static UserNotFoundInReportApiException forEmail(String email) {
        return new UserNotFoundInReportApiException("User with email '" + email + "' not found in the report API");
    }
}

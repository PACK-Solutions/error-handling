package com.ps.error_handling.external.exception;

/**
 * Exception thrown when a report for a specific user is not found. This exception indicates that the requested report
 * does not exist for the specified user.
 */
public class ReportForUserNotFoundException extends RuntimeException {

    /** Constructs a new ReportForUserNotFoundException with no detail message. */
    public ReportForUserNotFoundException() {
        super("Report not found for the specified user");
    }

    /**
     * Constructs a new ReportForUserNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public ReportForUserNotFoundException(String message) {
        super(message);
    }
}

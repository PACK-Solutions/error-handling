package com.ps.error_handling.external.exception;

/**
 * Exception thrown when an invalid report name is provided to the report client. This exception indicates that the
 * requested report name does not exist or is not valid.
 */
public class InvalidReportNameException extends RuntimeException {

    /** Constructs a new InvalidReportNameException with no detail message. */
    public InvalidReportNameException() {
        super("Invalid report name provided");
    }

    /**
     * Constructs a new InvalidReportNameException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidReportNameException(String message) {
        super(message);
    }
}

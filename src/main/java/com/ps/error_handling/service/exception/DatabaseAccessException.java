package com.ps.error_handling.service.exception;

/**
 * Exception thrown when there is an error accessing the database. This exception wraps the original exception that
 * occurred during database access.
 */
public class DatabaseAccessException extends RuntimeException {

    /**
     * Constructs a new DatabaseAccessException with the specified cause.
     *
     * @param cause the cause of the database access error
     */
    public DatabaseAccessException(Throwable cause) {
        super("Error accessing the database", cause);
    }
}

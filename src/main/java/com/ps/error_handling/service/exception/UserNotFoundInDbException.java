package com.ps.error_handling.service.exception;

/**
 * Exception thrown when a user is not found in the database. This exception indicates that the requested user does not
 * exist in the database.
 */
public class UserNotFoundInDbException extends RuntimeException {

    /**
     * Constructs a new UserNotFoundInDbException with the specified user ID.
     *
     * @param userId the ID of the user that was not found
     */
    public UserNotFoundInDbException(String userId) {
        super("User with ID '" + userId + "' not found in the database");
    }
}

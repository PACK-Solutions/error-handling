package com.ps.error_handling.model;

/**
 * Represents a user in the system. This record contains the user's ID and email address.
 *
 * @param userId the unique identifier of the user
 * @param email the email address of the user
 */
public record User(String userId, String email) {}

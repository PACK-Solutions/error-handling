package com.ps.error_handling.repository;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Repository;

import com.ps.error_handling.model.User;

/**
 * Repository for accessing user data. This is a simulated repository that randomly returns different outcomes for
 * demonstration purposes.
 */
@Repository
public class UserRepository {

    /**
     * Retrieves a user by their ID. This is a simulated method that randomly: - Throws a RuntimeException (10% chance)
     * - Returns an empty Optional (10% chance) - Returns a User with the provided userId and a hardcoded email (80%
     * chance)
     *
     * @param userId the ID of the user to retrieve
     * @return an Optional containing the user if found, or empty if not found
     * @throws RuntimeException randomly to simulate database errors
     */
    public Optional<User> getUser(String userId) {
        return switch (ThreadLocalRandom.current().nextInt(0, 10)) {
            case 0 -> throw new RuntimeException("Some DB error");
            case 1 -> Optional.empty();
            default -> Optional.of(new User(userId, userId + "@pack-solutions.com"));
        };
    }
}

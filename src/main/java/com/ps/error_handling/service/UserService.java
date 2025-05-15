package com.ps.error_handling.service;

import com.ps.error_handling.model.User;
import com.ps.error_handling.repository.UserRepository;
import com.ps.error_handling.service.exception.DatabaseAccessException;
import com.ps.error_handling.service.exception.UserNotFoundInDbException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for handling user-related operations.
 * This service provides methods for retrieving user information.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Constructs a new UserService with the specified repository.
     *
     * @param userRepository the repository for accessing user data
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the user with the specified ID
     * @throws DatabaseAccessException   if there is an error accessing the database
     * @throws UserNotFoundInDbException if no user with the specified ID is found
     */
    public User getUser(String userId) {
        Optional<User> optionalUser;
        try {
            optionalUser = userRepository.getUser(userId);
        } catch (Exception e) {
            throw new DatabaseAccessException(e);
        }
        return optionalUser.orElseThrow(() -> new UserNotFoundInDbException(userId));
    }
}

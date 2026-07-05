package com.projectarchitect.service;

import com.projectarchitect.dto.response.UserResponse;

import java.util.List;

/**
 * Service contract for administrative user management operations.
 */
public interface UserService {

    /**
     * Retrieves all registered users.
     */
    List<UserResponse> getAllUsers();

    /**
     * Deletes a user by their identifier.
     *
     * @param userId the identifier of the user to delete
     */
    void deleteUser(String userId);
}

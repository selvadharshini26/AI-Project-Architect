package com.projectarchitect.service;

import com.projectarchitect.dto.request.LoginRequest;
import com.projectarchitect.dto.request.RegisterRequest;
import com.projectarchitect.dto.response.AuthResponse;

/**
 * Service contract for user registration and authentication operations.
 */
public interface AuthService {

    /**
     * Registers a new user account with the {@code USER} role by default.
     *
     * @param request registration payload
     * @return authentication response containing the issued JWT
     */
    AuthResponse register(RegisterRequest request);

    /**
     * Authenticates a user using username/email and password.
     *
     * @param request login payload
     * @return authentication response containing the issued JWT
     */
    AuthResponse login(LoginRequest request);
}

package com.projectarchitect.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Response payload returned after successful login or registration,
 * containing the issued JWT and basic user information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;

    @Builder.Default
    private String tokenType = "Bearer";

    private String userId;

    private String username;

    private String email;

    private Set<String> roles;
}

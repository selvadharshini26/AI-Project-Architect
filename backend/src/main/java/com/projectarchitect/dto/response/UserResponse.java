package com.projectarchitect.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Public-facing representation of a user, excluding sensitive fields
 * such as the password hash.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String id;

    private String username;

    private String email;

    private Set<String> roles;

    private boolean enabled;

    private LocalDateTime createdAt;
}

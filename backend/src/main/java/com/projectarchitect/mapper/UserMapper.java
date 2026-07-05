package com.projectarchitect.mapper;

import com.projectarchitect.dto.response.UserResponse;
import com.projectarchitect.model.Role;
import com.projectarchitect.model.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Converts between {@link User} domain entities and their DTO representations.
 */
@Component
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        if (user == null) {
            return null;
        }
        Set<String> roleNames = user.getRoles().stream()
                .map(Role::name)
                .collect(Collectors.toSet());

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(roleNames)
                .enabled(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .build();
    }
}

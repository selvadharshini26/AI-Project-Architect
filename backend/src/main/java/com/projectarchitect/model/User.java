package com.projectarchitect.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * MongoDB document representing an application user.
 * Stores credentials, profile information and assigned roles.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    @Field("username")
    private String username;

    @Indexed(unique = true)
    @Field("email")
    private String email;

    @Field("password")
    private String password;

    @Builder.Default
    @Field("roles")
    private Set<Role> roles = new HashSet<>();

    @Builder.Default
    @Field("enabled")
    private boolean enabled = true;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;
}

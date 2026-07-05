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

/**
 * MongoDB document representing an AI-generated software architecture project.
 * Each project belongs to the user that requested its generation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "projects")
public class Project {

    @Id
    private String id;

    @Indexed
    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("architecture_details")
    private ArchitectureDetails architectureDetails;

    @Indexed
    @Field("owner_id")
    private String ownerId;

    @Field("owner_username")
    private String ownerUsername;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;
}

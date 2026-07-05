package com.projectarchitect.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response representation of a generated project, including its
 * full AI-generated architecture details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {

    private String id;

    private String title;

    private String description;

    private ArchitectureDetailsResponse architectureDetails;

    private String ownerId;

    private String ownerUsername;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

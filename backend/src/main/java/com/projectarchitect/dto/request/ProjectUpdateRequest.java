package com.projectarchitect.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request payload for updating an existing project's editable fields.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUpdateRequest {

    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;

    @Size(max = 3000, message = "Description must not exceed 3000 characters")
    private String description;
}

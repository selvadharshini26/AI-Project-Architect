package com.projectarchitect.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request payload used to ask the AI engine to generate a complete
 * software architecture for a given project idea.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectGenerateRequest {

    @NotBlank(message = "Project idea/title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;

    @NotBlank(message = "Project description is required")
    @Size(max = 3000, message = "Description must not exceed 3000 characters")
    private String description;

    /**
     * Optional free-text constraints e.g. "prefer microservices",
     * "must use Kafka", "budget-friendly deployment".
     */
    private String additionalConstraints;
}

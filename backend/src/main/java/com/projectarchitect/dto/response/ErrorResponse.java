package com.projectarchitect.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Standardized error response body returned by the global exception handler.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private boolean success;

    private int status;

    private String error;

    private String message;

    private String path;

    private List<String> details;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}

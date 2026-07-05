package com.projectarchitect.service;

import com.projectarchitect.dto.request.ProjectGenerateRequest;
import com.projectarchitect.model.ArchitectureDetails;

/**
 * Service contract for interacting with the Google Gemini generative AI API
 * to produce complete software architecture documents.
 */
public interface GeminiService {

    /**
     * Sends a structured prompt to Gemini based on the given project idea
     * and parses the response into a structured {@link ArchitectureDetails}.
     *
     * @param request the project idea/description supplied by the client
     * @return the fully populated architecture details
     */
    ArchitectureDetails generateArchitecture(ProjectGenerateRequest request);
}

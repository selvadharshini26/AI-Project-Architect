package com.projectarchitect.service;

import com.projectarchitect.dto.request.ProjectGenerateRequest;
import com.projectarchitect.dto.request.ProjectUpdateRequest;
import com.projectarchitect.dto.response.ProjectResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service contract for AI-assisted project generation and CRUD management.
 */
public interface ProjectService {

    /**
     * Generates a full software architecture using the Gemini AI service
     * and persists the resulting project for the currently authenticated user.
     */
    ProjectResponse generateProject(ProjectGenerateRequest request);

    /**
     * Retrieves all projects visible to the current user, paginated.
     * Administrators see all projects; regular users see only their own.
     */
    Page<ProjectResponse> getAllProjects(Pageable pageable);

    /**
     * Retrieves a single project by its identifier.
     */
    ProjectResponse getProjectById(String id);

    /**
     * Updates the editable fields of an existing project.
     */
    ProjectResponse updateProject(String id, ProjectUpdateRequest request);

    /**
     * Deletes a project by its identifier.
     */
    void deleteProject(String id);

    /**
     * Searches projects by (partial, case-insensitive) title match.
     */
    Page<ProjectResponse> searchProjectsByTitle(String title, Pageable pageable);
}

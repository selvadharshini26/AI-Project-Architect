package com.projectarchitect.controller;

import com.projectarchitect.dto.request.ProjectGenerateRequest;
import com.projectarchitect.dto.request.ProjectUpdateRequest;
import com.projectarchitect.dto.response.ApiResponse;
import com.projectarchitect.dto.response.ProjectResponse;
import com.projectarchitect.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.projectarchitect.constants.AppConstants.DEFAULT_PAGE_NUMBER;
import static com.projectarchitect.constants.AppConstants.DEFAULT_PAGE_SIZE;
import static com.projectarchitect.constants.AppConstants.DEFAULT_SORT_FIELD;

/**
 * REST controller exposing AI-powered project generation and full CRUD
 * management of generated software architecture projects.
 */
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Tag(name = "Projects", description = "AI-powered project architecture generation and management")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/generate")
    @Operation(summary = "Generate a complete software architecture using Gemini AI and save it")
    public ResponseEntity<ApiResponse<ProjectResponse>> generateProject(
            @Valid @RequestBody ProjectGenerateRequest request) {
        ProjectResponse response = projectService.generateProject(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Project architecture generated successfully", response));
    }

    @GetMapping
    @Operation(summary = "Retrieve all projects (own projects for USER, all projects for ADMIN)")
    public ResponseEntity<ApiResponse<Page<ProjectResponse>>> getAllProjects(
            @Parameter(description = "Zero-based page index") @RequestParam(defaultValue = "" + DEFAULT_PAGE_NUMBER) int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "" + DEFAULT_PAGE_SIZE) int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, DEFAULT_SORT_FIELD));
        Page<ProjectResponse> response = projectService.getAllProjects(pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/search")
    @Operation(summary = "Search projects by (partial) title")
    public ResponseEntity<ApiResponse<Page<ProjectResponse>>> searchProjects(
            @Parameter(description = "Title keyword to search for") @RequestParam String title,
            @RequestParam(defaultValue = "" + DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = "" + DEFAULT_PAGE_SIZE) int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, DEFAULT_SORT_FIELD));
        Page<ProjectResponse> response = projectService.searchProjectsByTitle(title, pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a project by its identifier")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProjectById(@PathVariable String id) {
        ProjectResponse response = projectService.getProjectById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a project's title/description")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(
            @PathVariable String id, @Valid @RequestBody ProjectUpdateRequest request) {
        ProjectResponse response = projectService.updateProject(id, request);
        return ResponseEntity.ok(ApiResponse.success("Project updated successfully", response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a project by its identifier")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok(ApiResponse.success("Project deleted successfully", null));
    }
}

package com.projectarchitect.service.impl;

import com.projectarchitect.dto.request.ProjectGenerateRequest;
import com.projectarchitect.dto.request.ProjectUpdateRequest;
import com.projectarchitect.dto.response.ProjectResponse;
import com.projectarchitect.exception.ResourceNotFoundException;
import com.projectarchitect.exception.UnauthorizedException;
import com.projectarchitect.mapper.ProjectMapper;
import com.projectarchitect.model.ArchitectureDetails;
import com.projectarchitect.model.Project;
import com.projectarchitect.repository.ProjectRepository;
import com.projectarchitect.security.SecurityUser;
import com.projectarchitect.service.GeminiService;
import com.projectarchitect.service.ProjectService;
import com.projectarchitect.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Implementation of {@link ProjectService} responsible for orchestrating
 * AI-based architecture generation via {@link GeminiService} and performing
 * full CRUD lifecycle management of {@link Project} documents.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final GeminiService geminiService;
    private final ProjectMapper projectMapper;
    private final SecurityUtil securityUtil;

    @Override
    @Transactional
    public ProjectResponse generateProject(ProjectGenerateRequest request) {
        SecurityUser currentUser = securityUtil.getCurrentUser();

        log.info("Generating AI architecture for project '{}' requested by user '{}'",
                request.getTitle(), currentUser.getUsername());

        ArchitectureDetails architectureDetails = geminiService.generateArchitecture(request);

        Project project = Project.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .architectureDetails(architectureDetails)
                .ownerId(currentUser.getId())
                .ownerUsername(currentUser.getUsername())
                .build();

        Project savedProject = projectRepository.save(project);
        log.info("Project '{}' generated and saved with id '{}'", savedProject.getTitle(), savedProject.getId());

        return projectMapper.toProjectResponse(savedProject);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectResponse> getAllProjects(Pageable pageable) {
        SecurityUser currentUser = securityUtil.getCurrentUser();

        Page<Project> projects = securityUtil.isCurrentUserAdmin()
                ? projectRepository.findAll(pageable)
                : projectRepository.findByOwnerId(currentUser.getId(), pageable);

        return projects.map(projectMapper::toProjectResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse getProjectById(String id) {
        Project project = findProjectOrThrow(id);
        assertOwnershipOrAdmin(project);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    @Transactional
    public ProjectResponse updateProject(String id, ProjectUpdateRequest request) {
        Project project = findProjectOrThrow(id);
        assertOwnershipOrAdmin(project);

        if (StringUtils.hasText(request.getTitle())) {
            project.setTitle(request.getTitle());
        }
        if (StringUtils.hasText(request.getDescription())) {
            project.setDescription(request.getDescription());
        }

        Project updatedProject = projectRepository.save(project);
        log.info("Project '{}' updated", updatedProject.getId());

        return projectMapper.toProjectResponse(updatedProject);
    }

    @Override
    @Transactional
    public void deleteProject(String id) {
        Project project = findProjectOrThrow(id);
        assertOwnershipOrAdmin(project);

        projectRepository.deleteById(id);
        log.info("Project '{}' deleted", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectResponse> searchProjectsByTitle(String title, Pageable pageable) {
        SecurityUser currentUser = securityUtil.getCurrentUser();

        Page<Project> projects = securityUtil.isCurrentUserAdmin()
                ? projectRepository.findByTitleContainingIgnoreCase(title, pageable)
                : projectRepository.findByOwnerIdAndTitleContainingIgnoreCase(currentUser.getId(), title, pageable);

        return projects.map(projectMapper::toProjectResponse);
    }

    private Project findProjectOrThrow(String id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
    }

    private void assertOwnershipOrAdmin(Project project) {
        SecurityUser currentUser = securityUtil.getCurrentUser();
        boolean isOwner = project.getOwnerId().equals(currentUser.getId());
        if (!isOwner && !securityUtil.isCurrentUserAdmin()) {
            throw new UnauthorizedException("You do not have permission to access this project");
        }
    }
}

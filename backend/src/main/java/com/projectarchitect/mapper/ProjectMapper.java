package com.projectarchitect.mapper;

import com.projectarchitect.dto.response.ArchitectureDetailsResponse;
import com.projectarchitect.dto.response.ProjectResponse;
import com.projectarchitect.model.ArchitectureDetails;
import com.projectarchitect.model.Project;
import org.springframework.stereotype.Component;

/**
 * Converts between {@link Project} domain entities and their DTO representations.
 */
@Component
public class ProjectMapper {

    public ProjectResponse toProjectResponse(Project project) {
        if (project == null) {
            return null;
        }
        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .architectureDetails(toArchitectureDetailsResponse(project.getArchitectureDetails()))
                .ownerId(project.getOwnerId())
                .ownerUsername(project.getOwnerUsername())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }

    public ArchitectureDetailsResponse toArchitectureDetailsResponse(ArchitectureDetails details) {
        if (details == null) {
            return null;
        }
        return ArchitectureDetailsResponse.builder()
                .problemStatement(details.getProblemStatement())
                .functionalRequirements(details.getFunctionalRequirements())
                .nonFunctionalRequirements(details.getNonFunctionalRequirements())
                .recommendedTechStack(details.getRecommendedTechStack())
                .highLevelArchitecture(details.getHighLevelArchitecture())
                .lowLevelArchitecture(details.getLowLevelArchitecture())
                .databaseDesign(details.getDatabaseDesign())
                .mongoCollections(details.getMongoCollections())
                .restApiSpecification(details.getRestApiSpecification())
                .microservicesSuggestion(details.getMicroservicesSuggestion())
                .folderStructure(details.getFolderStructure())
                .deploymentStrategy(details.getDeploymentStrategy())
                .securityRecommendations(details.getSecurityRecommendations())
                .ciCdRecommendation(details.getCiCdRecommendation())
                .developmentRoadmap(details.getDevelopmentRoadmap())
                .testingStrategy(details.getTestingStrategy())
                .futureEnhancements(details.getFutureEnhancements())
                .build();
    }
}

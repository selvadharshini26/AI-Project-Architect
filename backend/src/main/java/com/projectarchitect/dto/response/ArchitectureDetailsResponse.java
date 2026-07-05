package com.projectarchitect.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response representation of the AI-generated architecture details
 * for a project.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchitectureDetailsResponse {

    private String problemStatement;
    private List<String> functionalRequirements;
    private List<String> nonFunctionalRequirements;
    private List<String> recommendedTechStack;
    private String highLevelArchitecture;
    private String lowLevelArchitecture;
    private String databaseDesign;
    private List<String> mongoCollections;
    private String restApiSpecification;
    private String microservicesSuggestion;
    private String folderStructure;
    private String deploymentStrategy;
    private List<String> securityRecommendations;
    private String ciCdRecommendation;
    private String developmentRoadmap;
    private String testingStrategy;
    private List<String> futureEnhancements;
}

package com.projectarchitect.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Embedded document holding the full AI-generated software architecture
 * details for a {@link Project}. Populated by the Gemini AI service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchitectureDetails {

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

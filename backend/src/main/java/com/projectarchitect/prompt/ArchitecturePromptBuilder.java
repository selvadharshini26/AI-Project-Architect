package com.projectarchitect.prompt;

import com.projectarchitect.dto.request.ProjectGenerateRequest;
import org.springframework.stereotype.Component;

/**
 * Builds the structured prompt sent to the Google Gemini API to generate
 * a complete software architecture document. The prompt instructs the
 * model to return a strict JSON object so the response can be reliably
 * parsed into {@link com.projectarchitect.model.ArchitectureDetails}.
 */
@Component
public class ArchitecturePromptBuilder {

    /**
     * Builds the full prompt text for the given project generation request.
     *
     * @param request the user's project idea and constraints
     * @return the prompt string to send to Gemini
     */
    public String buildArchitecturePrompt(ProjectGenerateRequest request) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("You are a Senior Software Architect with 20+ years of experience designing ")
                .append("enterprise-grade, cloud-native systems. Based on the project idea below, ")
                .append("generate a COMPLETE software architecture document.\n\n");

        prompt.append("PROJECT TITLE: ").append(request.getTitle()).append("\n");
        prompt.append("PROJECT DESCRIPTION: ").append(request.getDescription()).append("\n");

        if (request.getAdditionalConstraints() != null && !request.getAdditionalConstraints().isBlank()) {
            prompt.append("ADDITIONAL CONSTRAINTS: ").append(request.getAdditionalConstraints()).append("\n");
        }

        prompt.append("\nRespond with ONLY a valid, minified JSON object (no markdown fences, no commentary) ")
                .append("that strictly matches the following schema. Every field must be present. ")
                .append("List fields must be JSON arrays of strings. String fields may contain multi-line, ")
                .append("well-formatted content using \\n for line breaks where helpful.\n\n");

        prompt.append("""
                {
                  "problemStatement": "string",
                  "functionalRequirements": ["string"],
                  "nonFunctionalRequirements": ["string"],
                  "recommendedTechStack": ["string"],
                  "highLevelArchitecture": "string",
                  "lowLevelArchitecture": "string",
                  "databaseDesign": "string",
                  "mongoCollections": ["string"],
                  "restApiSpecification": "string",
                  "microservicesSuggestion": "string",
                  "folderStructure": "string",
                  "deploymentStrategy": "string",
                  "securityRecommendations": ["string"],
                  "ciCdRecommendation": "string",
                  "developmentRoadmap": "string",
                  "testingStrategy": "string",
                  "futureEnhancements": ["string"]
                }
                """);

        prompt.append("\nGuidelines:\n")
                .append("- Be specific and practical, not generic.\n")
                .append("- Assume a modern cloud-native, microservices-friendly stack unless the description suggests otherwise.\n")
                .append("- restApiSpecification should list key endpoints with HTTP methods and short descriptions.\n")
                .append("- folderStructure should represent a realistic backend project tree as plain text.\n")
                .append("- Output must be valid JSON parsable by a standard JSON parser. Do not wrap it in markdown code fences.\n");

        return prompt.toString();
    }
}

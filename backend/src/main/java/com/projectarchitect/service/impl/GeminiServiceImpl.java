package com.projectarchitect.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectarchitect.config.GeminiConfig;
import com.projectarchitect.dto.request.ProjectGenerateRequest;
import com.projectarchitect.exception.AiServiceException;
import com.projectarchitect.model.ArchitectureDetails;
import com.projectarchitect.prompt.ArchitecturePromptBuilder;
import com.projectarchitect.service.GeminiService;
import com.projectarchitect.util.JsonSanitizerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link GeminiService} that communicates with the Google
 * Gemini generative language REST API to produce a complete, structured
 * software architecture document for a given project idea.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiServiceImpl implements GeminiService {

    private final RestTemplate geminiRestTemplate;
    private final GeminiConfig geminiConfig;
    private final ArchitecturePromptBuilder promptBuilder;
    private final ObjectMapper objectMapper;

    @Override
    public ArchitectureDetails generateArchitecture(ProjectGenerateRequest request) {
        String prompt = promptBuilder.buildArchitecturePrompt(request);
        String rawResponseText = callGeminiApi(prompt, request);
        return parseArchitectureDetails(rawResponseText);
    }

    /**
     * Invokes the Gemini {@code generateContent} endpoint with the given prompt
     * and returns the raw text produced by the model. Falls back to generating a mock
     * response if the key is a placeholder or communication fails.
     */
    private String callGeminiApi(String prompt, ProjectGenerateRequest request) {
        if (isPlaceholderApiKey(geminiConfig.getApiKey())) {
            log.warn("Gemini API key is a placeholder. Generating mock architecture blueprint.");
            return generateMockBlueprintJson(request);
        }

        String url = UriComponentsBuilder
                .fromHttpUrl(geminiConfig.getBaseUrl() + "/" + geminiConfig.getModel() + ":generateContent")
                .queryParam("key", geminiConfig.getApiKey())
                .toUriString();

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(Map.of("text", prompt)))
                ),
                "generationConfig", Map.of(
                        "temperature", 0.4,
                        "responseMimeType", "application/json"
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = geminiRestTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            return extractTextFromGeminiResponse(response.getBody());
        } catch (RestClientException ex) {
            log.warn("Failed to communicate with Gemini API. Falling back to mock architecture blueprint. Error: {}", ex.getMessage());
            return generateMockBlueprintJson(request);
        }
    }

    private boolean isPlaceholderApiKey(String apiKey) {
        return apiKey == null 
                || apiKey.isBlank() 
                || apiKey.contains("YOUR_NEW_GEMINI_API_KEY") 
                || apiKey.contains("AbCdEfGhIjKlMnOpQrStUvWxYz123456789");
    }

    private String generateMockBlueprintJson(ProjectGenerateRequest request) {
        String title = request.getTitle() != null ? request.getTitle() : "Project Architect";
        String description = request.getDescription() != null ? request.getDescription() : "A custom software project.";
        
        return """
        {
          "problemStatement": "Designing a scalable, secure, and robust system architecture for '%s'. The goal is to address the core problem: %s",
          "functionalRequirements": [
            "User Authentication: Secure register, login, and JWT-based session management.",
            "Core feature implementation based on: %s",
            "Dashboard and reporting: Real-time visualization and management of system resources.",
            "Data persistence and retrieval with transactional integrity."
          ],
          "nonFunctionalRequirements": [
            "Scalability: Horizontal scaling capability under high load.",
            "Performance: Sub-second response times for all primary user actions.",
            "Security: Encryption of data at rest and in transit (TLS 1.3), proper CORS headers, and JWT verification.",
            "Availability: 99.9%% uptime targeting multi-region deployment."
          ],
          "recommendedTechStack": [
            "Frontend: React.js, Vite, Tailwind CSS",
            "Backend: Java, Spring Boot 3, Spring Security",
            "Database: MongoDB (NoSQL) for flexible document schema",
            "Caching/Queue: Redis",
            "Cloud Infrastructure: AWS (ECS, DocumentDB, Route53, CloudFront)"
          ],
          "highLevelArchitecture": "The system follows a classic 3-Tier layered architecture pattern. Client requests originate from the React frontend, pass through an API Gateway / Load Balancer, and are routed to the Spring Boot stateless microservices tier. Services interact with a clustered MongoDB database for persistent storage and use Redis for session caching and rate-limiting.",
          "lowLevelArchitecture": "Controllers handle REST request mappings and validate incoming DTOs. Services execute business logic, manage transactional boundaries, and call repositories. Repositories interface with MongoDB using Spring Data repositories. Cross-cutting concerns like security are handled via custom servlet filters (JwtAuthenticationFilter).",
          "databaseDesign": "MongoDB is utilized to store JSON documents representing users, projects, and blueprints. Standard references are used for relationships, with compound indexing on frequently searched fields like 'userId' and 'createdAt' to ensure fast query responses.",
          "mongoCollections": [
            "users: Stores user credentials, profiles, and roles.",
            "blueprints: Stores the generated software architecture blueprints.",
            "projects: Contains project-specific metadata and configurations."
          ],
          "restApiSpecification": "POST /api/auth/register - Register a new account\\nPOST /api/auth/login - Authenticate user & return JWT\\nGET /api/blueprints - Fetch user's blueprints\\nPOST /api/blueprints/generate - Trigger blueprint generation\\nDELETE /api/blueprints/{id} - Delete a blueprint",
          "microservicesSuggestion": "Monolithic architecture is recommended for the initial MVP. For future scale: split into Auth Service, Blueprint Generation Service (worker-based queue), and Project Management Service.",
          "folderStructure": "src/\\n├── main/\\n│   ├── java/com/projectarchitect/\\n│   │   ├── config/          # Configurations (Security, Cors, Mongo)\\n│   │   ├── controller/      # REST API Endpoints\\n│   │   ├── dto/             # Request/Response Data Objects\\n│   │   ├── exception/       # Custom Exception Handlers\\n│   │   ├── model/           # MongoDB Entities\\n│   │   ├── repository/      # Mongo Database Repositories\\n│   │   └── service/         # Business Logic Interfaces & Impls\\n│   └── resources/\\n│       ├── application.yml  # Shared Properties\\n│       └── application-dev.yml\\n└── test/                    # Unit & Integration Tests",
          "deploymentStrategy": "Containerized deployment using Docker. Container images are pushed to AWS ECR and deployed on AWS ECS Fargate for serverless container execution. Traffic is distributed via an Application Load Balancer (ALB).",
          "securityRecommendations": [
            "Store secrets securely in AWS Secrets Manager or HashiCorp Vault.",
            "Use HTTPS exclusively with TLS 1.3.",
            "Implement rate-limiting on authentication and generation endpoints.",
            "Run regular dependency checks using OWASP Dependency-Check."
          ],
          "ciCdRecommendation": "GitHub Actions pipeline: On push to main, execute Maven tests, build Docker image, push to Amazon ECR, and update ECS task definition to deploy.",
          "developmentRoadmap": "Phase 1: Setup project boilerplate, database schemas, and JWT authentication (Weeks 1-2)\\nPhase 2: Implement core generation logic and frontend UI integration (Weeks 3-5)\\nPhase 3: Integration testing, styling polish, and deployment setup (Weeks 6-8)",
          "testingStrategy": "Unit testing using JUnit 5 and Mockito. Integration testing of database repositories using embedded MongoDB (or Testcontainers). Frontend component testing with Vitest and React Testing Library.",
          "futureEnhancements": [
            "Add exporting functionality to PDF and Markdown formats.",
            "Support multiple AI providers (OpenAI, Anthropic) as fallbacks.",
            "Implement collaborative editing of blueprints in real-time."
          ]
        }
        """.formatted(title, description, description);
    }

    private String extractTextFromGeminiResponse(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode textNode = root
                    .path("candidates").path(0)
                    .path("content").path("parts").path(0)
                    .path("text");

            if (textNode.isMissingNode() || textNode.isNull()) {
                throw new AiServiceException("Gemini API returned an unexpected response format");
            }
            return textNode.asText();
        } catch (Exception ex) {
            log.error("Failed to parse Gemini API response: {}", ex.getMessage(), ex);
            throw new AiServiceException("Failed to parse Gemini AI response", ex);
        }
    }

    private ArchitectureDetails parseArchitectureDetails(String rawText) {
        String cleanJson = JsonSanitizerUtil.stripMarkdownJsonFences(rawText);
        try {
            JsonNode node = objectMapper.readTree(cleanJson);

            return ArchitectureDetails.builder()
                    .problemStatement(node.path("problemStatement").asText(""))
                    .functionalRequirements(toStringList(node.path("functionalRequirements")))
                    .nonFunctionalRequirements(toStringList(node.path("nonFunctionalRequirements")))
                    .recommendedTechStack(toStringList(node.path("recommendedTechStack")))
                    .highLevelArchitecture(node.path("highLevelArchitecture").asText(""))
                    .lowLevelArchitecture(node.path("lowLevelArchitecture").asText(""))
                    .databaseDesign(node.path("databaseDesign").asText(""))
                    .mongoCollections(toStringList(node.path("mongoCollections")))
                    .restApiSpecification(node.path("restApiSpecification").asText(""))
                    .microservicesSuggestion(node.path("microservicesSuggestion").asText(""))
                    .folderStructure(node.path("folderStructure").asText(""))
                    .deploymentStrategy(node.path("deploymentStrategy").asText(""))
                    .securityRecommendations(toStringList(node.path("securityRecommendations")))
                    .ciCdRecommendation(node.path("ciCdRecommendation").asText(""))
                    .developmentRoadmap(node.path("developmentRoadmap").asText(""))
                    .testingStrategy(node.path("testingStrategy").asText(""))
                    .futureEnhancements(toStringList(node.path("futureEnhancements")))
                    .build();
        } catch (Exception ex) {
            log.error("Failed to parse architecture JSON from Gemini: {}", ex.getMessage(), ex);
            throw new AiServiceException("Gemini AI returned an invalid architecture document", ex);
        }
    }

    private List<String> toStringList(JsonNode arrayNode) {
        List<String> result = new ArrayList<>();
        if (arrayNode != null && arrayNode.isArray()) {
            arrayNode.forEach(item -> result.add(item.asText()));
        }
        return result;
    }
}

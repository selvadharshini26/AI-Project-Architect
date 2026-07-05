package com.projectarchitect.util;

/**
 * Utility for cleaning up raw text responses returned by the Gemini API
 * before attempting to parse them as JSON, since LLMs occasionally wrap
 * JSON output in markdown code fences despite instructions not to.
 */
public final class JsonSanitizerUtil {

    private JsonSanitizerUtil() {
        // Prevent instantiation
    }

    /**
     * Strips markdown code fences (e.g. ```json ... ```) and surrounding
     * whitespace from a raw LLM text response.
     */
    public static String stripMarkdownJsonFences(String rawText) {
        if (rawText == null) {
            return null;
        }
        String trimmed = rawText.trim();

        if (trimmed.startsWith("```")) {
            int firstNewline = trimmed.indexOf('\n');
            if (firstNewline != -1) {
                trimmed = trimmed.substring(firstNewline + 1);
            }
            int lastFence = trimmed.lastIndexOf("```");
            if (lastFence != -1) {
                trimmed = trimmed.substring(0, lastFence);
            }
        }
        return trimmed.trim();
    }
}

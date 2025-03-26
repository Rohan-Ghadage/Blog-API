package com.example.blog_app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class OpenAiService {

    // Inject API key from configuration
    @Value("${openrouter.api.key}")
    private String apiKey;

    // Correct endpoint for Chat Completions
    private final String OPENROUTER_URL = "https://openrouter.ai/api/v1/chat/completions";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public OpenAiService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public String generateSummary(String blogContent) {
        // Build the messages list
        List<Map<String, String>> messages = new ArrayList<>();
        // System message to set context for the model
        messages.add(Map.of("role", "system", "content", "You are a helpful assistant that summarizes blog content."));
        // User message with the prompt (include the blog content)
        messages.add(Map.of("role", "user", "content", "Summarize the following blog content:\n\n" + blogContent));

        // Build request body as a map
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", messages);
        requestBody.put("max_tokens", 100);
        requestBody.put("temperature", 0.7);

        // Prepare HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Build the HttpEntity with the headers and request body
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // Send POST request to the OpenAI API endpoint
            ResponseEntity<String> response = restTemplate.exchange(OPENROUTER_URL, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                // Parse JSON response to extract the summary
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode choices = root.path("choices");
                if (choices.isArray() && choices.size() > 0) {

                    JsonNode message = choices.get(0).path("message");
                    return message.path("content").asText().trim();
                }
            }
            return "Error: Could not generate summary.";
        } catch (HttpClientErrorException.TooManyRequests e) {
            return "❌ API quota exceeded. Please check your OpenAI billing details.";
        } catch (HttpClientErrorException.Unauthorized e) {
            return "❌ Invalid OpenAI API key. Please update your key.";
        } catch (HttpClientErrorException e) {
            return "❌ OpenAI API error: " + e.getMessage();
        } catch (Exception e) {
            return "❌ Unexpected error: " + e.getMessage();
        }
    }
}

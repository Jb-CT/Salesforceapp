package com.example.salesforceapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CleverTapUploader {
    private static final Logger logger = LoggerFactory.getLogger(CleverTapUploader.class);
    private static final String CLEVERTAP_API_URL = "https://us1.api.clevertap.com/1/upload";
    private static final String ACCOUNT_ID = "4R4-5KZ-8K7Z";
    private static final String PASSCODE = "AOA-JWD-CLEL";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public CleverTapUploader() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public boolean uploadLead(Map<String, Object> leadData) {
        try {
            // Create the CleverTap payload structure
            ObjectNode requestBody = objectMapper.createObjectNode();
            ArrayNode d = objectMapper.createArrayNode();
            d.add(objectMapper.valueToTree(leadData));
            requestBody.set("d", d);

            // Create the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(CLEVERTAP_API_URL))
                    .header("Content-Type", "application/json")
                    .header("X-CleverTap-Account-Id", ACCOUNT_ID)
                    .header("X-CleverTap-Passcode", PASSCODE)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            // Send the request
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            // Log the response
            logger.info("CleverTap API Response: {}", response.body());

            // Check if the upload was successful
            boolean isSuccess = response.statusCode() == 200;
            if (!isSuccess) {
                logger.error("Failed to upload to CleverTap. Status: {}, Response: {}",
                        response.statusCode(), response.body());
            }

            return isSuccess;

        } catch (Exception e) {
            logger.error("Error uploading to CleverTap", e);
            return false;
        }
    }
}





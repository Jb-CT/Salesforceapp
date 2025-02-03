package com.example.salesforceapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.HashMap;
import java.util.Map;

public class LeadDataMapper {
    private final ObjectMapper objectMapper;

    public LeadDataMapper() {
        this.objectMapper = new ObjectMapper();
    }

    public Map<String, Object> mapToCleverTapFormat(JsonNode salesforceData) {
        // Create the main payload object
        Map<String, Object> clevertapPayload = new HashMap<>();

        // Extract lead data safely
        String id = salesforceData.has("id") ? salesforceData.get("id").asText() : "N/A";
        String firstName = salesforceData.has("firstName") ? salesforceData.get("firstName").asText() : "";
        String lastName = salesforceData.has("lastName") ? salesforceData.get("lastName").asText() : "";
        String company = salesforceData.has("company") ? salesforceData.get("company").asText() : "N/A";
        String email = salesforceData.has("email") ? salesforceData.get("email").asText() : "N/A";

        // Combine firstName and lastName
        String fullName = (firstName + " " + lastName).trim();
        if (fullName.isEmpty()) {
            fullName = "N/A";
        }

        // Create the profile data object according to CleverTap's format
        ObjectNode profileData = objectMapper.createObjectNode()
                .put("Name", fullName)
                .put("Email", email)
                .put("Company", company)
                .put("Source", "Salesforce")
                .put("LeadId", id);

        // Build the final payload structure
        clevertapPayload.put("identity", id);  // Required by CleverTap
        clevertapPayload.put("type", "profile");  // Specify this is a profile update
        clevertapPayload.put("profileData", profileData);

        return clevertapPayload;
    }
}

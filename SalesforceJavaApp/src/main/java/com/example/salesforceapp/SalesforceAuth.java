package com.example.salesforceapp;

import java.net.http.*;
import java.net.URI;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SalesforceAuth {
    private static final String TOKEN_URL = "https://login.salesforce.com/services/oauth2/token";
    private static final String CLIENT_ID = "3MVG9GBhY6wQjl2sxZusrMPZ_tgC5w3Ne5j8KJ4.04l9ffs5._f7lzsYuPVmMsUdu3UlMMcp5HEDtnL_jyuuw";
    private static final String CLIENT_SECRET = "6FCBC5889CF422DDABC7655760CCFEA5175E61C6E8E839883E083259A9A6DCDC";
    private static final String USERNAME = "jash.bhurakhaya-n3zr@force.com";
    private static final String PASSWORD = "Jash@1234oX3gdsgZaMcvIzvVAlzNaEbb";

    public static String authenticate() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        String requestBody = String.format(
                "grant_type=password&client_id=%s&client_secret=%s&username=%s&password=%s",
                CLIENT_ID, CLIENT_SECRET, USERNAME, PASSWORD
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TOKEN_URL))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(response.body(), Map.class);
            return map.get("access_token").toString();
        } else {
            throw new Exception("Failed to authenticate: " + response.body());
        }
    }
}

package com.example.salesforceapp;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class SalesforceWebhook {

    private static final Logger logger = LoggerFactory.getLogger(SalesforceWebhook.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static HttpServer server;

    // Instantiate the LeadDataMapper and CleverTapUploader
    private static final LeadDataMapper leadDataMapper = new LeadDataMapper();
    private static final CleverTapUploader cleverTapUploader = new CleverTapUploader();

    // Starts the webhook server on port 8080
    public static void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/webhook/lead", new LeadHandler());
        server.setExecutor(null);  // Default executor
        server.start();
        logger.info("Server started on port 8080");
    }

    // Stops the server
    public static void stopServer() {
        if (server != null) {
            server.stop(1);
            logger.info("Server stopped.");
        }
    }

    // Handle incoming POST requests
    static class LeadHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                // Read the raw request body
                String rawRequestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                logger.info("Raw Salesforce Payload:\n{}", rawRequestBody);

                // Parse the lead data from the request body
                JsonNode leadData = objectMapper.readTree(rawRequestBody);

                // Use LeadDataMapper to map the data to CleverTap format
                Map<String, Object> clevertapData = leadDataMapper.mapToCleverTapFormat(leadData);

                // Upload the mapped data to CleverTap
                boolean uploadSuccess = cleverTapUploader.uploadLead(clevertapData);

                // Send response back to Salesforce
                String response = uploadSuccess ? "Lead uploaded successfully" : "Failed to upload lead";
                exchange.sendResponseHeaders(uploadSuccess ? 200 : 500, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                // Handle methods other than POST
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }

    // Main method to run the webhook server
    public static void main(String[] args) {
        try {
            startServer();
        } catch (IOException e) {
            logger.error("Error starting server", e);
        }
    }
}







//package com.example.salesforceapp;
//
//import com.sun.net.httpserver.HttpServer;
//import com.sun.net.httpserver.HttpHandler;
//import com.sun.net.httpserver.HttpExchange;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.InetSocketAddress;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import java.nio.charset.StandardCharsets;
//
//public class SalesforceWebhook {
//
//    private static final Logger logger = LoggerFactory.getLogger(SalesforceWebhook.class);
//    private static final ObjectMapper objectMapper = new ObjectMapper();
//
//    private static HttpServer server;
//
//    // Starts the webhook server on port 8080
//    public static void startServer() throws IOException {
//        server = HttpServer.create(new InetSocketAddress(8080), 0);
//        server.createContext("/webhook/lead", new LeadHandler());
//        server.setExecutor(null);  // Default executor
//        server.start();
//        logger.info("Server started on port 8080");
//    }
//
//    // Stops the server
//    public static void stopServer() {
//        if (server != null) {
//            server.stop(1);
//            logger.info("Server stopped.");
//        }
//    }
//
//    // Handle incoming POST requests
//    static class LeadHandler implements HttpHandler {
//        @Override
//        public void handle(HttpExchange exchange) throws IOException {
//            if ("POST".equals(exchange.getRequestMethod())) {
//                // Read the raw request body
////                String rawRequestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
////                logger.info("Raw Salesforce Payload:\n{}", rawRequestBody);
////                System.out.println("Raw Request Body: " + rawRequestBody);
//
//                // Parse the lead data from the request body
//                JsonNode leadData = objectMapper.readTree(exchange.getRequestBody());
//
//                // Log the received lead data
//                logger.info("Received lead data: {}", leadData.toPrettyString());
//
//
//                // Print the received lead data in a readable format
//                System.out.println("===== New Lead Received =====");
//                System.out.println("ID: " + leadData.get("id").asText());
//                System.out.println("Name: " + leadData.get("name").asText());
//                System.out.println("Company: " + leadData.get("company").asText());
//                System.out.println("Email: " + leadData.get("email").asText());
//                System.out.println("=============================");
//
//                // Send response back to Salesforce
//                String response = "Lead received successfully";
//                exchange.sendResponseHeaders(200, response.length());
//                OutputStream os = exchange.getResponseBody();
//                os.write(response.getBytes());
//                os.close();
//            } else {
//                // Handle methods other than POST
//                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
//            }
//        }
//    }
//}

//package com.example.salesforceapp;
//
//import com.sun.net.httpserver.HttpServer;
//import com.sun.net.httpserver.HttpHandler;
//import com.sun.net.httpserver.HttpExchange;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.InetSocketAddress;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import java.nio.charset.StandardCharsets;
//
//public class SalesforceWebhook {
//
//    private static final Logger logger = LoggerFactory.getLogger(SalesforceWebhook.class);
//    private static final ObjectMapper objectMapper = new ObjectMapper();
//
//    private static HttpServer server;
//
//    // Starts the webhook server on port 8080
//    public static void startServer() throws IOException {
//        server = HttpServer.create(new InetSocketAddress(8080), 0);
//        server.createContext("/webhook/lead", new LeadHandler());
//        server.setExecutor(null);  // Default executor
//        server.start();
//        logger.info("Server started on port 8080");
//    }
//
//    // Stops the server
//    public static void stopServer() {
//        if (server != null) {
//            server.stop(1);
//            logger.info("Server stopped.");
//        }
//    }
//
//    // Handle incoming POST requests
//    static class LeadHandler implements HttpHandler {
//        @Override
//        public void handle(HttpExchange exchange) throws IOException {
//            if ("POST".equals(exchange.getRequestMethod())) {
//                // Read the raw request body
//                String rawRequestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
//                logger.info("Raw Salesforce Payload:\n{}", rawRequestBody);
//
//                // Parse the lead data from the request body
//                JsonNode leadData = objectMapper.readTree(rawRequestBody);
//
//                // Extract lead fields safely
//                String id = leadData.has("id") ? leadData.get("id").asText() : "N/A";
//                String firstName = leadData.has("firstName") && !leadData.get("firstName").isNull() ? leadData.get("firstName").asText() : "";
//                String lastName = leadData.has("lastName") && !leadData.get("lastName").isNull() ? leadData.get("lastName").asText() : "";
//                String company = leadData.has("company") && !leadData.get("company").isNull() ? leadData.get("company").asText() : "N/A";
//                String email = leadData.has("email") && !leadData.get("email").isNull() ? leadData.get("email").asText() : "N/A";
//
//                // Combine firstName and lastName
//                String name = (firstName + " " + lastName).trim();
//                if (name.isEmpty()) {
//                    name = "N/A"; // Default if both first and last name are missing
//                }
//
//                // Log structured data
//                logger.info("Processed Lead Data - ID: {}, Name: {}, Company: {}, Email: {}", id, name, company, email);
//
//                // Print structured data
//                System.out.println("===== New Lead Received =====");
//                System.out.println("ID: " + id);
//                System.out.println("Name: " + name);
//                System.out.println("Company: " + company);
//                System.out.println("Email: " + email);
//                System.out.println("=============================");
//
//                // Send response back to Salesforce
//                String response = "Lead received successfully";
//                exchange.sendResponseHeaders(200, response.length());
//                OutputStream os = exchange.getResponseBody();
//                os.write(response.getBytes());
//                os.close();
//            } else {
//                // Handle methods other than POST
//                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
//            }
//        }
//    }
//
//    // Main method to run the webhook server
//    public static void main(String[] args) {
//        try {
//            startServer();
//        } catch (IOException e) {
//            logger.error("Error starting server", e);
//        }
//    }
//}



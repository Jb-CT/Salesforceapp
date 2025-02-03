# Salesforce to CleverTap Integration

This project fetches leads data from Salesforce and pushes it to CleverTap for better customer engagement and analytics. It includes a webhook to receive lead updates from Salesforce and sends the mapped data to CleverTap.

## Features
- Fetch leads from Salesforce using API authentication.
- Map Salesforce lead data to CleverTap's format.
- Push mapped data to CleverTap via API.
- Webhook to receive lead updates from Salesforce in real-time.
- Logging and error handling for smooth operations.

## Technologies Used
- **Java 17**
- **Maven**
- **Apache HttpClient**
- **Jackson (JSON Parsing)**
- **SLF4J & Logback (Logging)**
- **Sun HTTP Server (Webhook Handling)**

## Project Structure



##API Endpoints
- **Webhook Listener: /webhook/lead (Receives lead updates from Salesforce)**
- **CleverTap Data Upload: https://api.clevertap.com/1/upload**


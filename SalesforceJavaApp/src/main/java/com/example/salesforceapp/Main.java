package com.example.salesforceapp;

public class Main {
    public static void main(String[] args) {
        try {
            // Authenticate and get the access token
            SalesforceWebhook.startServer();
            String accessToken = SalesforceAuth.authenticate();
            System.out.println("Access Token: " + accessToken);

            // Fetch leads using the access token
            SalesforceClient.fetchLeads(accessToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//import java.io.IOException;
//
//public class Main {
//    public static void main(String[] args) {
//        try {
//            // Start the webhook server in a separate thread
//            new Thread(() -> {
//                try {
//                    SalesforceWebhook.startServer();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//
//            // Authenticate and get the access token
//            String accessToken = SalesforceAuth.authenticate();
//            System.out.println("Access Token: " + accessToken);
//
//            // Fetch leads using the access token
//            SalesforceClient.fetchLeads(accessToken);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}

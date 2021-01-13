package com.suraja.java;

import org.apache.http.HttpResponse;

import java.util.Scanner;

public class PostMethodCall {
    public static void main(String[] args) {
        Scanner scanner = null;

        try {
           String endPoint = "api/users";
           String jsonBody = "{\\n\" + \"    \\\"name\\\": \\\"morpheus\\\",\\n\" + \"    \\\"job\\\": \\\"leader\\\"\\n\" + \"}";
           //Call the request
           HtttpCallAction.POST(endPoint, jsonBody, HtttpCallAction.getDefaultClient());
           //Get the response
            HttpResponse response = HtttpCallAction.getResponse();
            System.out.println("Status-code -> " + response.getStatusLine().getStatusCode());
            // Print the response
            scanner = new Scanner(response.getEntity().getContent());
            while (scanner.hasNext()) {
                System.out.println(scanner.next());
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }
}

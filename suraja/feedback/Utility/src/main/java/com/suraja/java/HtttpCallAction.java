package com.suraja.java;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.net.URI;

public class HtttpCallAction {
    public static String baseURI = "http://localhost:8080";   //"http://reqres.in/"
    private static HttpResponse response;


    // Default client
    public static CloseableHttpClient getDefaultClient() {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        return closeableHttpClient;
    }

    // Post Method
    public static void POST(String endPoint, String jsonBody, CloseableHttpClient httpClient) {
        try {
            // Prepare the URL
            URI uri = new URIBuilder(baseURI + endPoint).build();
            //Post Request
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setHeader("content-type", "application/json");
            StringEntity stringEntity = new StringEntity(jsonBody);
            httpPost.setEntity(stringEntity);
            // Execute the request
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse != null) {
                response = httpResponse;
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }
    public static HttpResponse getResponse() {
        return response;
    }
}

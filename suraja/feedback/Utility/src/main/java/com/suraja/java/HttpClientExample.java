//package com.suraja.java;
//
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//import org.omg.DynamicAny.NameValuePair;
//
//import java.awt.*;
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class HttpClientExample {
//    // One instance, reuse
//    private final CloseableHttpClient httpClient = HttpClients.createDefault();
//
//    public static void main(String[] args) throws Exception{
//        HttpClientExample obj = new HttpClientExample();
//
//        try {
//            System.out.println("Testing 1");
//            obj.sendGet();
//
//            System.out.println("Testing 2");
//            obj.sendPost();
//        } finally {
//            obj.close();
//        }
//    }
//
//    private void close() throws IOException {
//        httpClient.close();
//    }
//
//    private void sendGet() throws Exception {
//        HttpGet request = new HttpGet("localhost:5000");
//
//        // add request handler
//        request.addHeader();
//    }
//
//    private void sendPost() throws Exception {
//        HttpPost post = new HttpPost("localhost:5000");
//
//        // add request parameter, from parameters
//        List<NameValuePair> urlParameters = new ArrayList<>();
//        urlParameters.add(new BasicNameValuePair("username", "abc"));
//        urlParameters.add(new BasicNameValuePair("password", "123"));
//        urlParameters.add(new BasicNameValuePair("custom", "secret"));
//
//        post.setEntity(new UrlEncodedFormEntity(urlParameters));
//
//        try (CloseableHttpClient httpClient = HttpClients.createDefault();
//            CloseableHttpResponse response = httpClient.execute(post)) {
//            System.out.println(EntityUtils.toString(response.getEntity()));
//        }
//    }
//}

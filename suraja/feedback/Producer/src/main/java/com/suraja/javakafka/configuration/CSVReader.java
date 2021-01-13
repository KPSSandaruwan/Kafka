package com.suraja.javakafka.configuration;


import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
    public static void main(String[] args) {
        String path = "/home/kpssandaruwan/Documents/input.csv";
        String line = "";

        // Creating mongodb connection
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://sandaruwan:suraja2228482@cluster0.kbhc2.mongodb.net/feedbackdb?retryWrites=true&w=majority"
        );

        try (MongoClient mongoClient = new MongoClient(uri)) {
            MongoDatabase database = mongoClient.getDatabase("feedbackdb");
            MongoCollection<Document> collection = database.getCollection("source");

            BufferedReader br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

//                // Create Json object
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("result", values[0].toString());
//                jsonObject.put("comment", values[1].toString());
                Document document = new Document();
                document.append("result", values[0].toString());
                document.append("comment", values[1].toString());
                database.getCollection("source").insertOne(document);
                System.out.println("Document insert successfully");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

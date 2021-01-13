package com.suraja.javakafka.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Mongo {

    public static void main(String[] args) {
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://sandaruwan:suraja2228482@cluster0.kbhc2.mongodb.net/feedbackdb?retryWrites=true&w=majority"
        );

        try (MongoClient mongoClient = new MongoClient(uri)){
            MongoDatabase db = mongoClient.getDatabase("feedbackdb");
            MongoCollection<Document> collection = db.getCollection("test");

            // Find One document
            Document samM51 = collection.find(new Document("item_type", "iphone7")).first();


            assert samM51 != null;
            System.out.println("iphone7: " + samM51.toJson());

            ObjectMapper mapper = new ObjectMapper();
            String json = null;
            try {
                json = mapper.writeValueAsString(samM51);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            System.out.println(json);
        }
    }
}


//5fe51e1009e034d554f3d7ef
package com.suraja.javakafka.configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ItemList {
    public static void main(String[] args) {
//        String[] itemList = {"iphone 6", "iphone 7", "iphone 8"};
//        for (String i : itemList) {
//
//        }



        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://sandaruwan:suraja2228482@cluster0.kbhc2.mongodb.net/feedbackdb?retryWrites=true&w=majority"
        );

        try (MongoClient mongoClient = new MongoClient(uri)){
            MongoDatabase db = mongoClient.getDatabase("feedbackdb");
            MongoCollection<Document> collection = db.getCollection("item");

            // Find One document
            Document samM51 = collection.find(new Document("needed_item", "iphone7")).first();
            assert samM51 != null;
            Object json = samM51.toJson();
            System.out.println(json);


//            JSONObject jsonObject = new JSONObject(samM51);
//            System.out.println(jsonObject);

//            String neededItem = "";
//            try {
//                JSONObject jo = new JSONObject(samM51);
//                neededItem = jo.getString("asd");
//            } catch (JSONException e) {
//                // Here the json object has a parsing problem and your string remains empty as defined
//                e.printStackTrace();
//            }
//            String neededItem = jsonObject.getString("needed_item");
        }
    }
}

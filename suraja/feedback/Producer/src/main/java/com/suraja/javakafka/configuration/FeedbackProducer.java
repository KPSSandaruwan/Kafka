package com.suraja.javakafka.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSONSerializers;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.kstream.Produced;
import org.bson.Document;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class FeedbackProducer {




    public static void main(String[] args) throws FileNotFoundException {
        String bootstrapServer = "127.0.0.1:9092";
        Properties properties = new Properties();

        // Kafka bootstrap server
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create safe producer
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
        properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");

        // High throughput producer
        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20");
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32 * 1024));

        // Leverage idempodent producer from kafka
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");


        //Creating a producer
        Producer<String, String> producer = new KafkaProducer<String, String>(properties);



        // Creating mongodb connection
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://sandaruwan:suraja2228482@cluster0.kbhc2.mongodb.net/feedbackdb?retryWrites=true&w=majority"
        );

        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("feedbackdb");
        MongoCollection<Document> collection = database.getCollection("source");
        FindIterable<Document> iterDoc = collection.find();



        int i = 0;
        while (true) {
//            System.out.println("Producing batch: " + i);
            try {
                Iterator it = iterDoc.iterator();
                while (it.hasNext()) {
                    Object doc = it.next();
                    ObjectMapper mapper = new ObjectMapper();

                    try {
                        String json = mapper.writeValueAsString(doc);
                        System.out.println(json);
                        ProducerRecord<String, String> record = new ProducerRecord<String, String>("new-feedback", json.toString());
                        producer.send(record);
                        Thread.sleep(100);
                        i += 1;
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            } catch (InterruptedException e) {
                break;
            }
        }
        producer.flush();
        producer.close();
    }
}

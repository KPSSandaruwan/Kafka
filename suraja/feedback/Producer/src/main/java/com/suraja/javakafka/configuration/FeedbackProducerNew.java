package com.suraja.javakafka.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Properties;

public class FeedbackProducerNew {

    Logger logger = LoggerFactory.getLogger(FeedbackProducerNew.class.getName());

    public FeedbackProducerNew() {}

    public static void main(String[] args) {

        new FeedbackProducerNew().run();
    }


    public void run() {
        logger.info("Setup");



        // Get the correct input product from the user an api call
        String itemType = "iphone6";


        // Connect to mongo db
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://sandaruwan:suraja2228482@cluster0.kbhc2.mongodb.net/feedbackdb?retryWrites=true&w=majority"
        );

        // Mongo client
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("feedbackdb");
        MongoCollection<Document> collection = database.getCollection("test");

        // Find One document
        Document item = collection.find(new Document("item_type", itemType)).first();

        assert item != null;
        System.out.println("iphone7: " + item.toJson());

//
//        FindIterable<Document> iterDoc = collection.find();
//        FindIterable<Document> iterDoc = collection.find({"" : " "});



        // Create Kafka producer
        KafkaProducer<String, String> producer = createKafkaProducer();


        // Adding Shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Stopping the application");
            logger.info("Shutting down the client from mongo");
            logger.info("Closing producer...");
            producer.close();
            logger.info("Done!");

        }));


        while (true) {
            try {
                ProducerRecord<String, String> record = new ProducerRecord<String, String>("new-feedback", item.toString());
                producer.send(record);
                System.out.println(record);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    //Old code
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        while (true) {
//            try {
////                Iterator it = iterDoc.iterator();
//                while (it.hasNext()) {
//                    Object doc = it.next();
//                    ObjectMapper mapper = new ObjectMapper();
//
//                    try {
//                        String json = mapper.writeValueAsString(doc);
//                        System.out.println(json);
//                        ProducerRecord<String, String> record = new ProducerRecord<String, String>("new-feedback", json.toString());
//                        producer.send(record);
//                        Thread.sleep(100);
//                    } catch (JsonProcessingException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } catch (InterruptedException e) {
//                break;
//            }
//        }
//    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public KafkaProducer<String, String> createKafkaProducer() {
        String bootstrapServer = "127.0.0.1:9092";


        // Creating properties
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
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        return producer;
    }
}

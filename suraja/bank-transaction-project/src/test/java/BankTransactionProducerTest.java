import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BankTransactionProducerTest {
    @Test
    public void newRandomTransactionTest() {
        ProducerRecord<String, String> record = BankTransactionsProducer.newRandomTransaction("jhon");
        String key = record.key();
        String value = record.value();

        assertEquals(key, "jhon");
//        System.out.println(value);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(value);
            assertEquals(node.get("name").asText(), "jhon");
            assertTrue("Amount should be less than 100", node.get("amount").asInt() < 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(value);
    }
}

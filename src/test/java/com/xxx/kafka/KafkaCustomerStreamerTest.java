package com.xxx.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = KafkaApplication.class)
@EmbeddedKafka(partitions = 1, topics = {"CustomerInput", "CustomerEVEN", "CustomerODD"}, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class KafkaCustomerStreamerTest {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    private Consumer<String, String> setUpConsumer(String topic) {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("test-group", "false", embeddedKafka);
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        ConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);
        Consumer<String, String> consumer = consumerFactory.createConsumer();
        embeddedKafka.consumeFromAnEmbeddedTopic(consumer, topic);
        return consumer;
    }

    @Test
    public void testEvenAgePublishToEvenTopic() throws InterruptedException {
        String message = "{\"firstName\":\"John\", \"lastName\":\"Doe\", \"dateOfBirth\":\"1990-01-01\"}";
        kafkaTemplate.send("CustomerInput", message);

        // Set up consumer for "CustomerEVEN" topic
        Consumer<String, String> consumer = setUpConsumer("CustomerEVEN");

        ConsumerRecord<String, String> record = KafkaTestUtils.getSingleRecord(consumer, "CustomerEVEN");

        assertThat(record).isNotNull();
        assertThat(record.value()).isEqualTo(message);
    }

    @Test
    public void testOddAgePublishToOddTopic() throws InterruptedException {
        String message = "{\"firstName\":\"Jane\", \"lastName\":\"Doe\", \"dateOfBirth\":\"1991-01-01\"}";
        kafkaTemplate.send("CustomerInput", message);

        // Set up consumer for "CustomerODD" topic
        Consumer<String, String> consumer = setUpConsumer("CustomerODD");

        ConsumerRecord<String, String> record = KafkaTestUtils.getSingleRecord(consumer, "CustomerODD");

        assertThat(record).isNotNull();
        assertThat(record.value()).isEqualTo(message);
    }
}


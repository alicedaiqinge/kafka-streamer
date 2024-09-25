package com.xxx.kafka;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class KafkaCustomerStreamerTest {

    @Autowired
    private KafkaCustomerStreamer kafkaCustomerStreamer;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void testCustomerEven() {
        // Prepare a customer with an even age
        String message = "John,Doe,2000-01-01"; // Age is 24

        kafkaCustomerStreamer.consume(message);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate, times(1)).send(eq("CustomerEVEN"), argumentCaptor.capture());

        assertEquals(message, argumentCaptor.getValue());
    }

    @Test
    public void testCustomerOdd() {
        // Prepare a customer with an odd age
        String message = "Jane,Doe,2001-01-01"; // Age is 23

        kafkaCustomerStreamer.consume(message);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate, times(1)).send(eq("CustomerODD"), argumentCaptor.capture());

        assertEquals(message, argumentCaptor.getValue());
    }
}

package com.xxx.kafka;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;

@SpringBootTest(classes = KafkaApplication.class)  
public class KafkaCustomerStreamerTest {

    @Autowired
    private KafkaCustomerStreamer kafkaCustomerStreamer;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void testCustomerEven() {
        String message = "John,Doe,2000-01-01"; // Age is 24
        kafkaCustomerStreamer.consume(message);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate, times(1)).send(eq("CustomerEVEN"), argumentCaptor.capture());

        assertEquals(message, argumentCaptor.getValue());
    }

    @Test
    public void testCustomerOdd() {
        String message = "Jane,Doe,2001-01-01"; // Age is 23
        kafkaCustomerStreamer.consume(message);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate, times(1)).send(eq("CustomerODD"), argumentCaptor.capture());

        assertEquals(message, argumentCaptor.getValue());
    }
}

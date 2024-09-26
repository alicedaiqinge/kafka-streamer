package com.xxx.kafka;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.xxx.kafka.model.Customer;

@Service
public class KafkaCustomerStreamer {

    private static final String EVEN_TOPIC = "CustomerEVEN";
    private static final String ODD_TOPIC = "CustomerODD";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "CustomerInput", groupId = "customer-group")
    public void consume(String message) {
        String[] parts = message.split(",");
        Customer customer = new Customer(parts[0], parts[1], LocalDate.parse(parts[2]));

        if (isEvenTopic(customer.getAge())) {
            kafkaTemplate.send(EVEN_TOPIC, message);
        } else {
            kafkaTemplate.send(ODD_TOPIC, message);
        }
    }
    
    private boolean isEvenTopic(Integer age) {
    	if (age % 2 == 0) {
    		return true;
    	}
    	return false;
    }
}

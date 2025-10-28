package com.example.userservice.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RawStringConsumer {

    // Separate group id so it does not share offsets with the JSON consumer
    @KafkaListener(
            topics = "${KAFKA_TOPIC}",
            groupId = "user-service-string-test",
            containerFactory = "stringKafkaListenerContainerFactory")
    public void consumeRaw(String message) {
        System.out.println("[RAW STRING CONSUMER] Received: " + message);
    }
}

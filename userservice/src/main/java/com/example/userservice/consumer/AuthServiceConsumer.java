package com.example.userservice.consumer;

import com.example.userservice.entities.UserInfo;
import com.example.userservice.model.UserSignUpEventDto;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceConsumer {

    private final UserService userService;

    @Autowired
    public AuthServiceConsumer(UserService userService) {
        this.userService = userService;
    }

    @KafkaListener(topics = "${KAFKA_TOPIC}" , groupId = "${KAFKA_GROUP}")
    public void consume(UserSignUpEventDto eventData) {
        try {
            userService.createOrUpdateUser(eventData);
            System.out.println("Received Kafka message: " + eventData);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

package com.example.expenseservice.consumer;

import com.example.expenseservice.dto.ExpenseDto;
import com.example.expenseservice.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseConsumer {

    private final ExpenseService expenseService;

    @KafkaListener(topics = "${spring.kafka.consumer.topic-name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ExpenseDto expenseDto){
        try {
            expenseService.saveExpense(expenseDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

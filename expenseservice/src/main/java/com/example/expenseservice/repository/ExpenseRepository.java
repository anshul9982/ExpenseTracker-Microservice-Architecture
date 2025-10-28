package com.example.expenseservice.repository;

import com.example.expenseservice.entity.Expense;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {

    List<Expense> findByUserId(String userId);
    List<Expense> findByUserIdAndDateBetween(String userId, LocalDate createdAtStart, LocalDate createdAtEnd);
    List<Expense> findByUserIdAndDateAndTimeBetween(String userId, LocalDate date, LocalTime startTime, LocalTime endTime);

    Optional<Expense> findByUserIdAndExternalId(String userId, String externalId);

    List<Expense> findByUserIdAndDate(String userId, LocalDate date);

    List<Expense> findByUserIdAndMerchant(String userId, String merchant);
}

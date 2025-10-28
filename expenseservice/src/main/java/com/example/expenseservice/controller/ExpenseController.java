package com.example.expenseservice.controller;

import com.example.expenseservice.dto.ExpenseDto;
import com.example.expenseservice.service.ExpenseService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expense/getExpenses")
    public ResponseEntity<List<ExpenseDto>> getAllExpenses(@RequestHeader("X-Claim-Userid") String userId){
        try {
            List<ExpenseDto> expenseDtoList = expenseService.getAllExpenses(userId);
            return ResponseEntity.ok(expenseDtoList);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/expense/v1/all/{date}")
    public ResponseEntity<List<ExpenseDto>> getAllExpensesByDate(@RequestHeader("X-Claim-Userid") String userId, @PathVariable LocalDate date){
        try {
            List<ExpenseDto> expenseDtoList = expenseService.getAllExpensesByDate(userId, date);
            return ResponseEntity.ok(expenseDtoList);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/expense/v1/all/{merchant}")
    public ResponseEntity<List<ExpenseDto>> getAllExpensesByMerchant(@RequestHeader("X-Claim-Userid") String userId, @PathVariable String merchant){
        try {
            List<ExpenseDto> expenseDtoList = expenseService.getAllExpensesByMerchant(userId, merchant);
            return ResponseEntity.ok(expenseDtoList);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/expense/v1/all/{startDate}/{endDate}")
    public ResponseEntity<List<ExpenseDto>> getAllExpensesBetweenDates(@RequestHeader("X-Claim-Userid") String userId, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate){
        try {
            List<ExpenseDto> expenseDtoList = expenseService.getAllExpensesBetweenDates(userId, startDate, endDate);
            return ResponseEntity.ok(expenseDtoList);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/expense/v1/add")
    public ResponseEntity<ExpenseDto> addExpense(@RequestHeader("X-Claim-Userid") String userId, @RequestBody ExpenseDto expenseDto){
        try {
            expenseDto.setUserId(userId);  // Set the userId from header
            ExpenseDto expenseDto1 = expenseService.saveExpense(expenseDto);
            return ResponseEntity.ok(expenseDto1);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}

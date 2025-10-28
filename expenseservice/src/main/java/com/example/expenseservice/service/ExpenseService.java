package com.example.expenseservice.service;

import com.example.expenseservice.dto.ExpenseDto;
import com.example.expenseservice.entity.Expense;
import com.example.expenseservice.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseDto saveExpense(ExpenseDto expenseDto){
        setCurrency(expenseDto);
        setAmount(expenseDto);
        setMerchant(expenseDto);
        setTransactionType(expenseDto);
        setBalance(expenseDto);
        setDescription(expenseDto);
        setDate(expenseDto);
        setTime(expenseDto);
        
        try {
            Expense expense = Expense.builder()
                    .userId(expenseDto.getUserId())
                    .externalId(expenseDto.getExternalId())
                    .amount(expenseDto.getAmount())
                    .currency(expenseDto.getCurrency())
                    .merchant(expenseDto.getMerchant())
                    .transactionType(expenseDto.getTransactionType())
                    .balance(expenseDto.getBalance())
                    .description(expenseDto.getDescription())
                    .date(expenseDto.getDate())
                    .time(expenseDto.getTime())
                    .build();
            
            expenseRepository.save(expense);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return expenseDto;
    }

    public boolean updateExpense(ExpenseDto expenseDto){
        Optional<Expense> optionalExpense = expenseRepository.findByUserIdAndExternalId(expenseDto.getUserId(), expenseDto.getExternalId());
        if(optionalExpense.isEmpty()){
            return false;
        }
        Expense expense = optionalExpense.get();
        expense.setCurrency(Strings.isNotBlank(expenseDto.getCurrency())?expenseDto.getCurrency():expense.getCurrency());
        expense.setAmount(expenseDto.getAmount());
        expense.setMerchant(Strings.isNotBlank(expenseDto.getMerchant())?expenseDto.getMerchant():expense.getMerchant());
        expense.setTransactionType(Strings.isNotBlank(expenseDto.getTransactionType())?expenseDto.getTransactionType():expense.getTransactionType());
        expense.setBalance(Strings.isNotBlank(expenseDto.getBalance())?expenseDto.getBalance():expense.getBalance());
        expense.setDescription(Strings.isNotBlank(expenseDto.getDescription())?expenseDto.getDescription():expense.getDescription());
        expenseRepository.save(expense);
        return true;

    }

    public List<ExpenseDto> getAllExpenses(String userId){
       return expenseRepository.findByUserId(userId).stream().map(expense -> {ExpenseDto dto = new ExpenseDto(); dto.setExternalId(expense.getExternalId()); dto.setUserId(expense.getUserId()); dto.setAmount(expense.getAmount()); dto.setCurrency(expense.getCurrency()); dto.setMerchant(expense.getMerchant()); dto.setTransactionType(expense.getTransactionType()); dto.setBalance(expense.getBalance()); dto.setDescription(expense.getDescription()); return dto;}).toList();
    }

    public List<ExpenseDto> getAllExpensesByDate(String userId, LocalDate date){
       List<Expense> expenseList = expenseRepository.findByUserIdAndDate(userId, date);
       return expenseList.stream().map(expense -> {
           ExpenseDto dto = new ExpenseDto();
           dto.setExternalId(expense.getExternalId());
           dto.setUserId(expense.getUserId());
           dto.setAmount(expense.getAmount());
           dto.setCurrency(expense.getCurrency());
           dto.setMerchant(expense.getMerchant());
           dto.setTransactionType(expense.getTransactionType());
           dto.setBalance(expense.getBalance());
           dto.setDescription(expense.getDescription());
           dto.setDate(expense.getDate());
           dto.setTime(expense.getTime());
           return dto;
       }).toList();
    }
    public List<ExpenseDto> getAllExpensesBetweenDates(String userId, LocalDate startDate, LocalDate endDate){
       List<Expense> expenseList = expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
       return expenseList.stream().map(expense -> {
           ExpenseDto dto = new ExpenseDto();
           dto.setExternalId(expense.getExternalId());
           dto.setUserId(expense.getUserId());
           dto.setAmount(expense.getAmount());
           dto.setCurrency(expense.getCurrency());
           dto.setMerchant(expense.getMerchant());
           dto.setTransactionType(expense.getTransactionType());
           dto.setBalance(expense.getBalance());
           dto.setDescription(expense.getDescription());
           dto.setDate(expense.getDate());
           dto.setTime(expense.getTime());
           return dto;
       }).toList();
    }
    public List<ExpenseDto> getAllExpensesByMerchant(String userId, String merchant){
       List<Expense> expenseList = expenseRepository.findByUserIdAndMerchant(userId, merchant);
       return expenseList.stream().map(expense -> {
           ExpenseDto dto = new ExpenseDto();
           dto.setExternalId(expense.getExternalId());
           dto.setUserId(expense.getUserId());
           dto.setAmount(expense.getAmount());
           dto.setCurrency(expense.getCurrency());
           dto.setMerchant(expense.getMerchant());
           dto.setTransactionType(expense.getTransactionType());
           dto.setBalance(expense.getBalance());
           dto.setDescription(expense.getDescription());
           dto.setDate(expense.getDate());
           dto.setTime(expense.getTime());
           return dto;
       }).toList();
    }

    public void setCurrency(ExpenseDto expenseDto){
        if (Objects.isNull(expenseDto.getCurrency())){
            expenseDto.setCurrency("INR");
        }
    }

    public void setAmount(ExpenseDto dto){
        if (Objects.isNull(dto.getAmount())){
            dto.setAmount("0");
        }
        else{
            dto.setAmount(dto.getAmount());
        }
    }

    public void setMerchant(ExpenseDto dto){
        if (Objects.isNull(dto.getMerchant())){
            dto.setMerchant("Unknown");
        }
        else{
            dto.setMerchant(dto.getMerchant());
        }
    }

    public void setTransactionType(ExpenseDto dto){
        if (Objects.isNull(dto.getTransactionType())){
            dto.setTransactionType("Unknown");
        }
        else{
            dto.setTransactionType(dto.getTransactionType());
        }
    }

    public void setBalance(ExpenseDto dto){
        if (Objects.isNull(dto.getBalance())){
            dto.setBalance("0");
        }
        else{
            dto.setBalance(dto.getBalance());
        }
    }

    public void setDescription(ExpenseDto dto){
        if (Objects.isNull(dto.getDescription())){
            dto.setDescription("Unknown");
        }
        else{
            dto.setDescription(dto.getDescription());
        }
    }

    public void setDate(ExpenseDto dto){
        if (Objects.isNull(dto.getDate())){
            dto.setDate(LocalDate.now());
        }
        else{
            dto.setDate(dto.getDate());
        }
    }

    public void setTime(ExpenseDto dto){
        if (Objects.isNull(dto.getTime())){
            dto.setTime(LocalTime.now());
        }
        else{
            dto.setTime(dto.getTime());
        }
    }

    public void setUserId(ExpenseDto dto){
        if (Objects.isNull(dto.getUserId())){
            dto.setUserId("NULL");
        }
        else{
            dto.setUserId(dto.getUserId());
        }
    }
}

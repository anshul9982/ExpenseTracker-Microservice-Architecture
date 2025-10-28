package com.example.expenseservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseDto {

    private String externalId;

    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("amount")
    @NonNull
    private String amount;

    @JsonProperty("balance")
    private String balance;

    @JsonProperty("currency")
    private String currency;
    @JsonProperty("description")
    private String description;

    @JsonProperty("merchant")
    private String merchant;

    @JsonProperty("transaction_type")
    private String transactionType;

    @JsonProperty("date")
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;


}

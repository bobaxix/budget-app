package com.budget.budgetapp.entities.payment;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "plannedPayments")
public class PlannedPayment {

    @Id
    private String id;

    private String category;
    private String subcategory;
    private double amount;

    @JsonFormat(pattern = "yyyy/MM")
    private LocalDate date;

}
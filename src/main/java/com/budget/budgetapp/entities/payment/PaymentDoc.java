package com.budget.budgetapp.entities.payment;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "payments")
@Data
public class PaymentDoc {

    @Id
    private String id;

    private String category;
    private String subcategory;
    private double amount;

}
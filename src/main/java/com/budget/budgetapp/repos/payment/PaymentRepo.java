package com.budget.budgetapp.repos.payment;

import com.budget.budgetapp.entities.payment.PaymentDoc;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends MongoRepository<PaymentDoc, String> { }
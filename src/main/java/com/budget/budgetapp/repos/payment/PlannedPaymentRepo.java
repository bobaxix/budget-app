package com.budget.budgetapp.repos.payment;

import java.time.LocalDate;
import java.util.List;

import com.budget.budgetapp.entities.payment.PlannedPayment;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlannedPaymentRepo extends MongoRepository<PlannedPayment, String> {

    List<PlannedPayment> findByDateBetween(LocalDate start, LocalDate end);

}
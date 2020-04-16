package com.budget.budgetapp.repos.payment;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import com.budget.budgetapp.entities.payment.PaymentDoc;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends MongoRepository<PaymentDoc, String> { 

    List<PaymentDoc> findByDateBetween(LocalDate start, LocalDate end);

    List<PaymentDoc> findByDateBetweenAndCategory(LocalDate start, LocalDate end, String category);

    List<PaymentDoc> findByDateBetweenAndCategoryAndSubcategory(LocalDate start,
                                                                LocalDate end, 
                                                                String category, 
                                                                String subcategory);

}
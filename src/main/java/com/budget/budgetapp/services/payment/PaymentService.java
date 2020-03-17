package com.budget.budgetapp.services.payment;

import java.util.List;

import com.budget.budgetapp.entities.payment.PaymentDoc;
import com.budget.budgetapp.repos.payment.PaymentRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepo paymentRepository;

    public List<PaymentDoc> getAll() {
        return paymentRepository.findAll();
    }

    public void createNewPayment(PaymentDoc payment) {
        paymentRepository.insert(payment);
    }

}
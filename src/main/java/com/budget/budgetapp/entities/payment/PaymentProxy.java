package com.budget.budgetapp.entities.payment;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PaymentProxy {

    private List<PaymentDoc> payments;

    public PaymentProxy(List<PaymentDoc> payments) {
        this.payments = payments;
    }

    public List<PaymentDoc> getPaymentsForDay(int day) {
        
        List<PaymentDoc> listForDay = this.payments.stream()
                                .filter(payment -> payment.getDate().getDayOfMonth() == day)
                                .collect(Collectors.toList());

        return listForDay;

    }

    public List<PaymentDoc> getAllPayments() {
        return this.payments;
    }
    
}
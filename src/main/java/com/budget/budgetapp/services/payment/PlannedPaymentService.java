package com.budget.budgetapp.services.payment;

import java.util.List;

import com.budget.budgetapp.beans.DateUnpacker;
import com.budget.budgetapp.beans.PolishYearMonth;
import com.budget.budgetapp.beans.SearchDate;
import com.budget.budgetapp.entities.payment.PlannedPayment;
import com.budget.budgetapp.repos.payment.PlannedPaymentRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlannedPaymentService {

    @Autowired
    private PlannedPaymentRepo plannedPaymentRepo;

    @Autowired
    private DateUnpacker dateUnpacker;

    public List<PlannedPayment> getForYearMonth(PolishYearMonth yearMonth) {

        SearchDate date = dateUnpacker.unpackDate(yearMonth);
        return plannedPaymentRepo.findByDateBetween(date.getStartDate(), date.getEndDate());
    }
}
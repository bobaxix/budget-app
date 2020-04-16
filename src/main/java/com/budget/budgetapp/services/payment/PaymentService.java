package com.budget.budgetapp.services.payment;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import com.budget.budgetapp.beans.PolishYearMonth;
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

    public void removePaymentById(String id) {
        paymentRepository.deleteById(id);
    }

    public List<PaymentDoc> getForYearMonth(PolishYearMonth date) {
        LocalDate end = date.startOfNextMonth();
        LocalDate start = date.endOfPreviousMonth();
        return paymentRepository.findByDateBetween(start, end);
    }

    public List<PaymentDoc> getForYearAndMonthAndCategory(PolishYearMonth date, String category) {
        LocalDate end = date.startOfNextMonth();
        LocalDate start = date.endOfPreviousMonth();

        return paymentRepository.findByDateBetweenAndCategory(start, end, category);
    }

    public List<PaymentDoc> getFullFiltered(PolishYearMonth date,
                                            String category, 
                                            String subcategory) {

            LocalDate end = date.startOfNextMonth();
            LocalDate start = date.endOfPreviousMonth();

            return paymentRepository.findByDateBetweenAndCategoryAndSubcategory(start, end, category, subcategory);
    }

}
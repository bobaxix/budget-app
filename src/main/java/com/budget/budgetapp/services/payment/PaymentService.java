package com.budget.budgetapp.services.payment;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import com.budget.budgetapp.beans.DateUnpacker;
import com.budget.budgetapp.beans.PolishYearMonth;
import com.budget.budgetapp.beans.SearchDate;
import com.budget.budgetapp.entities.payment.PaymentDoc;
import com.budget.budgetapp.repos.payment.PaymentRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepo paymentRepository;

    @Autowired
    private DateUnpacker dateUnpacker;

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

        SearchDate searchDate = dateUnpacker.unpackDate(date);
        return paymentRepository.findByDateBetween(searchDate.getStartDate(),
                                                   searchDate.getEndDate());
    }

    public List<PaymentDoc> getForYearAndMonthAndCategory(PolishYearMonth date, String category) {
        SearchDate searchDate = dateUnpacker.unpackDate(date);
        return paymentRepository.findByDateBetweenAndCategory(searchDate.getStartDate(),
                                                              searchDate.getEndDate(),
                                                              category);
    }

    public List<PaymentDoc> getForYearAndMonthAndSubcategory(PolishYearMonth date, String subcategory) {
        SearchDate searchDate = dateUnpacker.unpackDate(date);
        return paymentRepository.findByDateBetweenAndSubcategory(searchDate.getStartDate(),
                                                              searchDate.getEndDate(),
                                                              subcategory);
    }
    public List<PaymentDoc> getFullFiltered(PolishYearMonth date,
                                            String category,
                                            String subcategory) {

            SearchDate searchDate = dateUnpacker.unpackDate(date);
            return paymentRepository.findByDateBetweenAndCategoryAndSubcategory(searchDate.getStartDate(),
                                                                                searchDate.getEndDate(),
                                                                                category,
                                                                                subcategory);
    }

}
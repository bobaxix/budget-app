package com.budget.budgetapp.controllers.payment;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.budget.budgetapp.beans.PaymentSummarizer;
import com.budget.budgetapp.beans.PaymentSummaryResult;
import com.budget.budgetapp.beans.PolishYearMonth;
import com.budget.budgetapp.entities.category.CategoryDoc;
import com.budget.budgetapp.entities.payment.PaymentDoc;
import com.budget.budgetapp.entities.payment.PaymentSum;
import com.budget.budgetapp.entities.payment.PlannedPayment;
import com.budget.budgetapp.payment.Category;
import com.budget.budgetapp.payment.Payment;
import com.budget.budgetapp.payment.Subcategory;
import com.budget.budgetapp.payment.Transaction;
import com.budget.budgetapp.payment.TransactionNode;
import com.budget.budgetapp.services.category.CategoryService;
import com.budget.budgetapp.services.payment.PaymentService;
import com.budget.budgetapp.services.payment.PlannedPaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/payment")
public class SummaryController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PlannedPaymentService plannedPaymentService;

    @GetMapping(value = "/summary") 
    public String getSummaryForMonth(@RequestParam(required = false) Integer year,
                                     @RequestParam(required = false) Integer month,
                                     Model theModel) {
        
        PolishYearMonth date = (year == null || month == null) ? 
            PolishYearMonth.now() : PolishYearMonth.of(year, month); 
        
        List<PaymentDoc> paymentList = paymentService.getForYearMonth(date);

        TransactionNode transactionManager = plannedPaymentService.getForYearMonth(date);        

        paymentList.forEach(el -> {

            String category = el.getCategory();
            String subcategory = el.getCategory();

            Transaction t = transactionManager.getNode(el.getCategory());
            t = t.getNode(el.getSubcategory());

            t.addNode(new Payment(el.getAmount()));
        });

        Map<String, Double> categoryDiff = transactionManager.getNodes()
                                                .stream()
                                                .collect(Collectors.toMap(
                                                    Transaction::getName, Transaction::getMoneyDiff));

        theModel.addAttribute("yearMonth", date);
        theModel.addAttribute("transactionManager", transactionManager);
        theModel.addAttribute("categoryDiff", categoryDiff);

        theModel.addAttribute("globalDiff", transactionManager.getMoneyDiff());

        return "summary";

    }
    




}
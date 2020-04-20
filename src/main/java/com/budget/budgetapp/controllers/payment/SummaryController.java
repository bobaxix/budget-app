package com.budget.budgetapp.controllers.payment;

import java.util.List;

import com.budget.budgetapp.beans.PolishYearMonth;
import com.budget.budgetapp.entities.category.CategoryDoc;
import com.budget.budgetapp.entities.payment.PaymentDoc;
import com.budget.budgetapp.entities.payment.PlannedPayment;
import com.budget.budgetapp.payment.TransactionManager;
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
        List<CategoryDoc> categoryList = categoryService.getAllCategories();
        List<PlannedPayment> plannedPaymentList = plannedPaymentService.getForYearMonth(date);

        TransactionManager transactionManager = new TransactionManager(categoryList);       
        transactionManager.addPayment(paymentList);
        transactionManager.addPlannedPayment(plannedPaymentList);
        
                
        theModel.addAttribute("yearMonth", date);
        theModel.addAttribute("transactionManager", transactionManager);
        theModel.addAttribute("categoryDiff", transactionManager.getCategoryDiff());
        theModel.addAttribute("globalDiff", transactionManager.getGlobalDiff());

        return "summary";

    }
    




}
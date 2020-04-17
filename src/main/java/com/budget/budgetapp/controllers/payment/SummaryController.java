package com.budget.budgetapp.controllers.payment;

import java.util.List;
import java.util.Map;

import com.budget.budgetapp.beans.PaymentSummarizer;
import com.budget.budgetapp.beans.PaymentSummaryResult;
import com.budget.budgetapp.beans.PolishYearMonth;
import com.budget.budgetapp.entities.category.CategoryDoc;
import com.budget.budgetapp.entities.payment.PaymentDoc;
import com.budget.budgetapp.services.category.CategoryService;
import com.budget.budgetapp.services.payment.PaymentService;

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

    @GetMapping(value = "/summary") 
    public String getSummaryForMonth(@RequestParam(required = false) Integer year,
                                     @RequestParam(required = false) Integer month,
                                     Model theModel) {
        
        PolishYearMonth date = (year == null || month == null) ? 
            PolishYearMonth.now() : PolishYearMonth.of(year, month); 
        
        List<PaymentDoc> paymentList = paymentService.getForYearMonth(date);
        List<CategoryDoc> categoryList = categoryService.getAllCategories();

        PaymentSummarizer paymentSummarizer = new PaymentSummarizer.Builder(paymentList)
                                                        .setCategoryList(categoryList)
                                                        .build();

        paymentSummarizer.doSummaryForSubcategories();
        paymentSummarizer.doSummaryForCategories();
        paymentSummarizer.doSummaryForGlobal();

        PaymentSummaryResult paymentSummary = paymentSummarizer.getResult();

        theModel.addAttribute("paymentMap", paymentSummary.getSummaryPerSubcategory());
        theModel.addAttribute("paymentSum", paymentSummary.getSummaryPerCategory());
        theModel.addAttribute("sumOfPaymentsInDate", paymentSummary.getSummaryPerPeriod());
        theModel.addAttribute("yearMonth", date);

        return "summary";

    }
    




}
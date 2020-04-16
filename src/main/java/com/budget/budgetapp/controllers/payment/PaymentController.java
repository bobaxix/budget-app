package com.budget.budgetapp.controllers.payment;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import com.budget.budgetapp.beans.PaymentSummarizer;
import com.budget.budgetapp.beans.PaymentSummaryResult;
import com.budget.budgetapp.beans.PolishYearMonth;
import com.budget.budgetapp.entities.category.CategoryDoc;
import com.budget.budgetapp.entities.payment.PaymentDoc;
import com.budget.budgetapp.entities.payment.PaymentProxy;
import com.budget.budgetapp.services.category.CategoryService;
import com.budget.budgetapp.services.payment.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/payment")
    public String getPaymentThymeleafView(@RequestParam(required = false) Integer year,
                                          @RequestParam(required = false) Integer month,
                                          @RequestParam(required = false, defaultValue = "") String category,
                                          @RequestParam(required = false, defaultValue = "") String subcategory,
                                          Model theModel) {
        
        // Need to validate date
        PolishYearMonth yearMonth;
        List<PaymentDoc> paymentsList;

        if (year == null || month == null) {
            yearMonth = PolishYearMonth.now();
        } else {
            yearMonth = PolishYearMonth.of(year, month);
        }

        if (!category.isEmpty() && !subcategory.isEmpty()) {
            paymentsList = paymentService.getFullFiltered(yearMonth, category, subcategory);
        } else {
            if (!category.isEmpty()) {
                paymentsList = paymentService.getForYearAndMonthAndCategory(yearMonth, category);
            } else if (!subcategory.isEmpty()) {
                paymentsList = paymentService.getForYearAndMonthAndSubcategory(yearMonth, subcategory);
            } else {
                paymentsList = paymentService.getForYearMonth(yearMonth);
            }
        }

        PaymentProxy paymentProxy = new PaymentProxy(paymentsList);

        PaymentSummarizer paymentSummarizer = new PaymentSummarizer.Builder(paymentsList)
                                                    .build();
        paymentSummarizer.doSummaryPerDay();
        PaymentSummaryResult summary = paymentSummarizer.getResult();

        theModel.addAttribute("paymentProxy", paymentProxy);
        theModel.addAttribute("yearMonth", yearMonth);
        theModel.addAttribute("daySummary", summary.getSummaryPerDay());

        return "payments-thymeleaf.html";
    }

    @GetMapping("/payment/add")
    public String getPaymentAdderView(Model theModel) {
        // Part realized dynamically by JS 
        return "add";
    }

    @PostMapping("/payment/new")
    public String postPayment(@ModelAttribute PaymentDoc payment) {
        System.out.println(payment);
        return "payments-thymeleaf.html";
    }


    // @GetMapping("/payment") 
    // public String getPaymentView() {
    //     return "payments.html";
    // }
}
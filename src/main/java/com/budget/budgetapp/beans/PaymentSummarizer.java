package com.budget.budgetapp.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.budget.budgetapp.entities.category.CategoryDoc;
import com.budget.budgetapp.entities.payment.PaymentDoc;

public class PaymentSummarizer {

    // Co trzeba ?
    // 1. Podsumowanie per subkategoria
    // 2. Podsumowanie per kategoria
    // 3. Podsumowanie per łączne
    // 4. W czym problem ? Najłatiwej jest to liczyć w kolejności podanej sekwencji

    private Map<String, Map<String, Double>> subcategorySummary;
    private Map<String, Double> categorySummary;

    private List<PaymentDoc> paymentList;

    private PaymentSummaryResult summaryResult = new PaymentSummaryResult();

    public static class Builder {

        private List<PaymentDoc> paymentList = null;
        private Map<String, Map<String, Double>> paymentMap = null;
        private Map<String, Double> categoryPaymentMap = null;

        public Builder(List<PaymentDoc> paymentList) { 
            this.paymentList = paymentList;
        }

        public Builder setCategoryList(List<CategoryDoc> categoryList) {
            
            paymentMap = new HashMap<>();  
            categoryPaymentMap = new HashMap<>();

            categoryList.forEach((category) -> {
                // Create subcategory map
                Map<String, Double> subcategoryMapper = new HashMap<>();
                paymentMap.put(category.getName(), subcategoryMapper);
                category.getSubcategories().forEach((sub) -> {
                    subcategoryMapper.put(sub, 0.0);
                });

                // Create category map
                categoryPaymentMap.put(category.getName(), 0.0);
            });

            return this;
        }

        public PaymentSummarizer build() {
            return new PaymentSummarizer(paymentList, paymentMap, categoryPaymentMap);
        }

    }

    private PaymentSummarizer(List<PaymentDoc> paymentList, 
                              Map<String, Map<String, Double>> subcategorySummary,
                              Map<String, Double> categorySummary) {

        this.paymentList = paymentList;
        this.subcategorySummary = subcategorySummary;
        this.categorySummary = categorySummary;

    }

    public void doSummaryForSubcategories() {
        
        paymentList.forEach(payment -> {
            
            String category = payment.getCategory();
            String subcategory = payment.getSubcategory();

            Map<String, Double> subcategoryMap = subcategorySummary.get(category);
            
            Double value = subcategoryMap.get(subcategory);
            
            value += payment.getAmount();

            subcategoryMap.put(subcategory, value);
            
        });

        summaryResult.setSummaryPerSubcategory(subcategorySummary);
    }

    public void doSummaryForCategories() {
        
        paymentList.forEach(payment -> {
            double value = categorySummary.getOrDefault(payment.getCategory(), 0.0);
            value += payment.getAmount();
            categorySummary.put(payment.getCategory(), value);
        });
    
        summaryResult.setSummaryPerCategory(categorySummary);
    }

    public void doSummaryForGlobal() {
        
        double globalSummary = paymentList.stream()
                .mapToDouble(mapper -> mapper.getAmount())
                .sum();
        
        summaryResult.setSummaryPerPeriod(globalSummary);
    }

    public PaymentSummaryResult getResult() {
        return summaryResult;
    }
}
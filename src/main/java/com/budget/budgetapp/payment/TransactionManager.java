package com.budget.budgetapp.payment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.budget.budgetapp.entities.category.CategoryDoc;
import com.budget.budgetapp.entities.payment.PaymentDoc;
import com.budget.budgetapp.entities.payment.PlannedPayment;

import lombok.Getter;

public class TransactionManager {

    private Double moneySpent = Double.NaN;
    private Double moneyExpected = Double.NaN;

    @Getter
    private Map<String, CategoryNode> categoryNodes = new HashMap<>();

    public TransactionManager(List<CategoryDoc> categoryList) {
        createFromCategoryList(categoryList);
    }

    private void createFromCategoryList(List<CategoryDoc> categoryList) {
        
        categoryList.forEach(el -> {

            String category = el.getName();
            List<String> subcategory = el.getSubcategories();

            CategoryNode categoryNode = new CategoryNode();

            Map<String, SubcategoryNode> subcategoryMap = subcategory.stream()
                                                            .collect(Collectors.toMap(
                                                                k -> k, v -> new SubcategoryNode()
                                                            ));
            
            categoryNode.setSubcategories(subcategoryMap);
            categoryNodes.putIfAbsent(category, categoryNode);

        });
    }

    public double getExpectedMoney() {

        if (moneyExpected.isNaN()) {
            if (categoryNodes != null) {
                moneyExpected = categoryNodes.values() 
                                    .stream()
                                    .mapToDouble(m -> m.getExpectedMoney())
                                    .sum();

                moneyExpected = (double)Math.round(moneyExpected * 100.0) / 100.0;
            } else {
                moneyExpected = 0.0;
            }
        }

        return moneyExpected;
    }

    public double getSpentMoney() {
        
        if (moneySpent.isNaN()) {
            if (categoryNodes != null) {
                moneySpent = categoryNodes.values() 
                    .stream()
                    .mapToDouble(m -> m.getSpentMoney())
                    .sum();
                moneySpent = (double)Math.round(moneySpent * 100.0) / 100.0;
            } else {
                moneySpent = 0.0;
            }
            
        }
        
        return moneySpent;
    }
    
    public void addPayment(PaymentDoc payment) {

        String category = payment.getCategory();
        String subcategory = payment.getSubcategory();

        CategoryNode c = categoryNodes.get(category);

        if (c == null) {
            // throw PaymentException("Category not found");
        }

        SubcategoryNode s = c.get(subcategory);
        s.addPayment(payment.getAdderUser(), payment.getAmount());
    }

    public void addPayment(List<PaymentDoc> payment) {
        payment.forEach(p -> addPayment(p));
    }

    public void addPlannedPayment(PlannedPayment plannedPayment) {
        String category = plannedPayment.getCategory();
        String subcategory = plannedPayment.getSubcategory();

        CategoryNode c = categoryNodes.get(category);

        if (c == null) {
            // throw PaymentException("Category not found");
        }

        SubcategoryNode s = c.get(subcategory);
        s.updateExpectedMoney(plannedPayment.getAmount());
        
    }

    public void addPlannedPayment(List<PlannedPayment> pp) {
        pp.forEach(p -> addPlannedPayment(p));
    }

    public Map<String, Double> getCategoryDiff() {

        Map<String, Double> categoryDiff = new HashMap<>();

        categoryNodes.entrySet()
                     .forEach(el -> {
                         categoryDiff.put(el.getKey(), el.getValue().getDiffMoney());
                     });

        return categoryDiff;
    }

    public double getGlobalDiff() {

        return categoryNodes.values()
                    .stream()
                    .mapToDouble(m -> m.getDiffMoney())
                    .sum();
    }

}
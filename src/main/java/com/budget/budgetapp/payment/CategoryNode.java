package com.budget.budgetapp.payment;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CategoryNode {

    private Double spentMoney = Double.NaN;
    private Double expectedMoney = Double.NaN;

    @Setter
    @Getter
    private Map<String, SubcategoryNode> subcategories = new HashMap<>();
    
    public double getExpectedMoney() {

        if (expectedMoney.isNaN()) {
            if (subcategories != null) {
                expectedMoney = subcategories.values()
                                    .stream()
                                    .mapToDouble(m -> m.getExpectedMoney())
                                    .sum();

                expectedMoney = (double)Math.round(expectedMoney * 100.0) / 100.0;
            } else {
                expectedMoney = 0.0;
            }
        }

        return expectedMoney;
    }

    public double getSpentMoney() {
        
        if (spentMoney.isNaN()) {
            if (subcategories != null) {
                spentMoney = subcategories.values()
                    .stream()
                    .mapToDouble(m -> m.getSpentMoney())
                    .sum();
                spentMoney = (double)Math.round(spentMoney * 100.0) / 100.0;
            } else {
                spentMoney = 0.0;
            }
            
        }
        
        return spentMoney;
    }

    public double getDiffMoney() {
        return getExpectedMoney() - getSpentMoney();
    }

    public SubcategoryNode get(String s) {
        
        SubcategoryNode p = subcategories.get(s);

        if (p == null) {
            // throw PaymentException
        }

        return p;
    }

}
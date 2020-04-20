package com.budget.budgetapp.payment;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
public class SubcategoryNode {

    @Getter
    private double expectedMoney = 0.0;
    private Double spentMoney = Double.NaN;

    @Getter
    private List<PaymentNode> payments = new ArrayList<>();

    public double getSpentMoney() {
        
        if (spentMoney.isNaN()) {
            if (payments != null) {
                spentMoney = payments.stream()
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
        return expectedMoney - getSpentMoney();
    }

    public void updateExpectedMoney(double expectedMoney) {
        this.expectedMoney += expectedMoney;
    }

    public void addPayment(String whoAdd, double amount) {
        payments.add(new PaymentNode(amount, whoAdd));
    }

}
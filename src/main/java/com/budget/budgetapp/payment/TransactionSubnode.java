package com.budget.budgetapp.payment;

import lombok.ToString;

@ToString(callSuper = true)
public class TransactionSubnode extends Transaction {

    private final double expectedMoney;
    public Double moneySpent = Double.NaN;

    public TransactionSubnode(String name) {
        super(name);
        this.expectedMoney = 0.0;
    }

    public TransactionSubnode(String name, double expectedMoney) {
        super(name);
        this.expectedMoney = expectedMoney;
    }

    @Override
    public double getMoneyExpected() {
        return expectedMoney;
    }

    @Override
    public double getMoneySpent() {
        
        if (moneySpent.isNaN()) {
            if (nodes != null) {
                moneySpent = nodes.stream()
                    .mapToDouble(m -> m.getMoneySpent())
                    .sum();
                moneySpent = (double)Math.round(moneySpent * 100.0) / 100.0;
            } else {
                moneySpent = 0.0;
            }
            
        }
        
        return moneySpent;
    }
    

}
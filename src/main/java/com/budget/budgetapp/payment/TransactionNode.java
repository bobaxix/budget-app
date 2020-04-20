package com.budget.budgetapp.payment;

import lombok.ToString;

@ToString(callSuper = true)
public class TransactionNode extends Transaction {

    private Double moneySpent = Double.NaN;
    private Double moneyExpected = Double.NaN;

    public TransactionNode(String name) {
        super(name);
    }

    public TransactionNode() {
        super();
    }

    @Override
    public double getMoneyExpected() {

        if (moneyExpected.isNaN()) {
            if (nodes != null) {
                moneyExpected = nodes.stream()
                    .mapToDouble(m -> m.getMoneyExpected())
                    .sum();

                moneyExpected = (double)Math.round(moneyExpected * 100.0) / 100.0;
            } else {
                moneyExpected = 0.0;
            }
        }

        return moneyExpected;
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
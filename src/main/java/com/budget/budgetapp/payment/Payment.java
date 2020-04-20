package com.budget.budgetapp.payment;

import lombok.ToString;

@ToString(callSuper = true)
public class Payment extends Transaction {

    private final double moneySpent;

    public Payment(double moneySpent) {
        super();
        this.moneySpent = moneySpent;
    }

    @Override
    public double getMoneyExpected() {
        return 0.0;
    }

    @Override
    public double getMoneySpent() {
        return moneySpent;
    }

    

}
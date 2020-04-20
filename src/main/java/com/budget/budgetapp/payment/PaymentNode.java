package com.budget.budgetapp.payment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString(callSuper = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PaymentNode {

    @Getter
    private final double spentMoney;

    @Getter
    private final String whoAdd; 

}
package com.budget.budgetapp.beans;

import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class PaymentSummaryResult {

    private Map<String, Map<String, Double>> summaryPerSubcategory;
    private Map<String, Double> summaryPerCategory;
    private Double summaryPerPeriod;


}
package com.budget.budgetapp.beans;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchDate {
    final private LocalDate endDate;
    final private LocalDate startDate;
}
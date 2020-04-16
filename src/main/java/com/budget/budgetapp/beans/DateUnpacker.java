package com.budget.budgetapp.beans;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class DateUnpacker {

    public SearchDate unpackDate(PolishYearMonth date) {

        LocalDate end = date.startOfNextMonth();
        LocalDate start = date.endOfPreviousMonth();

        return new SearchDate(end, start);
    }

}
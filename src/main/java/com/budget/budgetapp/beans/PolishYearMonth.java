package com.budget.budgetapp.beans;

import java.time.LocalDate;
import java.time.YearMonth;

public class PolishYearMonth {
    
    final private YearMonth yearMonth;

    private PolishYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    static public PolishYearMonth of(int year, int month) {
        return new PolishYearMonth(YearMonth.of(year, month));
    }

    static public PolishYearMonth now() {
        return new PolishYearMonth(YearMonth.now());
    }

    public int lengthOfMonth() {
        return yearMonth.lengthOfMonth();
    }

    public PolishMonth getMonth() {
        int month = yearMonth.getMonthValue();
        PolishMonth polishMonth = PolishMonth.valueOf(month);
        return polishMonth;
    }

    public int getMonthValue() {
        return yearMonth.getMonthValue();
    }

    public LocalDate atDay(int dayOfMonth) {
        return yearMonth.atDay(dayOfMonth);
    }

    public LocalDate endOfPreviousMonth() {
        YearMonth previousMonth = yearMonth.minusMonths(1);
        return previousMonth.atEndOfMonth();
    }

    public LocalDate startOfNextMonth() {
        return yearMonth.plusMonths(1).atDay(1);
    }

    public int getYear() {
        return yearMonth.getYear();
    }

    public PolishYearMonth previousMonth() {
        YearMonth prevMonth = yearMonth.minusMonths(1);
        return new PolishYearMonth(prevMonth);
    }

    public PolishYearMonth nextMonth() {
        YearMonth nextMonth = yearMonth.plusMonths(1);
        return new PolishYearMonth(nextMonth);
    }
}

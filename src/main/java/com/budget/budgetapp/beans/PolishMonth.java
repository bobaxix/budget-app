package com.budget.budgetapp.beans;

public enum PolishMonth {
    STYCZEŃ(1),
    LUTY(2),
    MARZEC(3),
    KWIECIEŃ(4),
    MAJ(5),
    CZERWIEC(6),
    LIPIEC(7),
    SIERPIEŃ(8),
    WRZESIEŃ(9),
    PAŹDZIERNIK(10),
    LISTOPAD(11),
    GRUDZIEŃ(12);

    private int numericValue;

    private PolishMonth(int numericValue) {
        this.numericValue = numericValue;
    }

    @Override
    public String toString() {
        String name = this.name();
        name = name.substring(0, 1) + name.substring(1).toLowerCase();
        return name;
    }

    static PolishMonth valueOf(int value) {

        if (value > PolishMonth.GRUDZIEŃ.numericValue || value < PolishMonth.STYCZEŃ.numericValue) {
            throw new IllegalArgumentException("Month can be in range between 1 and 12");
        }

        PolishMonth values = PolishMonth.values()[value - 1];
        return values;

    }
}
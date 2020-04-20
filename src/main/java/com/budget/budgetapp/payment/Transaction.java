package com.budget.budgetapp.payment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@ToString
public abstract class Transaction {

    protected final String name;
    protected List<Transaction> nodes;

    public abstract double getMoneySpent();
    public abstract double getMoneyExpected();
    
    public double getMoneyDiff() {
        double val = getMoneyExpected() - getMoneySpent();
        return Math.round(val * 100.0) / 100.0;
    }

    public void addNode(Transaction t) {
        
        if (nodes == null) {
            nodes = new ArrayList<>();
        }

        nodes.add(t);
    }


    public Transaction getNode(String name) {
        
        Transaction t = null;

        if (nodes != null) {

            Optional<Transaction> o =  nodes.stream()
                                        .filter(el -> name.equals(el.name))
                                        .findAny(); 

            if (o.isPresent()) {
                t = o.get();
            }
        }       

        return t;
    }
}
package com.budget.budgetapp.services.payment;

import java.util.List;

import com.budget.budgetapp.beans.DateUnpacker;
import com.budget.budgetapp.beans.PolishYearMonth;
import com.budget.budgetapp.beans.SearchDate;
import com.budget.budgetapp.repos.payment.PlannedPaymentRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.budget.budgetapp.entities.payment.PlannedPayment;
import com.budget.budgetapp.payment.TransactionNode;
import com.budget.budgetapp.payment.TransactionSubnode;

@Component
public class PlannedPaymentService {

    @Autowired
    private PlannedPaymentRepo plannedPaymentRepo;

    @Autowired
    private DateUnpacker dateUnpacker;

    public TransactionNode getForYearMonth(PolishYearMonth yearMonth) {

        SearchDate date = dateUnpacker.unpackDate(yearMonth);
        List<PlannedPayment> pp = plannedPaymentRepo.findByDateBetween(date.getStartDate(), date.getEndDate());

        return packPlannedListIntoTransactionChain(pp);
    }

    private TransactionNode packPlannedListIntoTransactionChain(List<PlannedPayment> pp) {
        
        TransactionNode transactionManager = new TransactionNode("transaction-manager");

        pp.forEach(el -> {

            String category = el.getCategory();
            String subcategory = el.getSubcategory();

            TransactionNode categoryNode = (TransactionNode)transactionManager.getNode(category);
            TransactionSubnode subcategoryNode = new TransactionSubnode(subcategory, el.getAmount());

            /* 
             * If category exists in chain, add subcategory to it,
             * if not, create new category node and then add it to chain
             */
            if (categoryNode != null) {
                categoryNode.addNode(subcategoryNode);
            } else {
                categoryNode  = new TransactionNode(category);
                categoryNode.addNode(subcategoryNode);
                transactionManager.addNode(categoryNode);
            }
        });

        return transactionManager;
    }

}
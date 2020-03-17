package com.budget.budgetapp.controllers.payment;

import java.util.List;

import com.budget.budgetapp.entities.payment.PaymentDoc;
import com.budget.budgetapp.services.payment.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    @GetMapping("/payment")
    public List<PaymentDoc> getAllPayments() {
        List<PaymentDoc> listOfPayments = paymentService.getAll();

        listOfPayments.forEach(p -> System.out.println(p));

        return listOfPayments;
    }

    @PostMapping("/payment")
    public PaymentDoc createNewPayment(@RequestBody PaymentDoc payment) {
        System.out.println(payment);
        paymentService.createNewPayment(payment);
        return payment;
    }
}
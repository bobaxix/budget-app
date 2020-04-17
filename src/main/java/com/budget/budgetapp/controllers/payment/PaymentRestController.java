package com.budget.budgetapp.controllers.payment;

import java.security.Principal;
import java.util.List;

import com.budget.budgetapp.entities.payment.PaymentDoc;
import com.budget.budgetapp.services.payment.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PaymentRestController {

    @Autowired
    private PaymentService paymentService;


    @GetMapping("/payment")
    public List<PaymentDoc> getAllPayments() {
        List<PaymentDoc> listOfPayments = paymentService.getAll();

        listOfPayments.forEach(p -> System.out.println(p));

        return listOfPayments;
    }

    @PostMapping("/payment")
    public PaymentDoc createNewPayment(@RequestBody PaymentDoc payment, Principal principal) {
        payment.setAdderUser(principal.getName());
        paymentService.createNewPayment(payment);
        return payment;
    }

    @DeleteMapping("/payment/{id}")
    public String deletePayment(@PathVariable String id) {
        paymentService.removePaymentById(id);
        System.out.println(id);
        return id;
    }
}
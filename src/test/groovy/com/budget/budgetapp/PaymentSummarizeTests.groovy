package com.budget.budgetapp

import com.budget.budgetapp.entities.payment.PaymentDoc
import com.budget.budgetapp.entities.category.CategoryDoc
import com.budget.budgetapp.beans.PaymentSummarizer
import com.budget.budgetapp.beans.PaymentSummaryResult

import spock.lang.Specification
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PaymentSummarizerTests extends Specification {
    
        def category_1 = Stub(CategoryDoc) {
            getName() >> "higiena"
            getSubcategories() >> ["kosmetyki", "chemia"]
        }

        def category_2 = Stub(CategoryDoc) {
            getName() >> "jedzenie"
            getSubcategories() >> ["na mieście", "w pracy"]
        }

        def categoryList = [category_1, category_2]

    def "Test for summary specified per subcategories"() {

        given:

            def paymentsList = []

            def data = [[
                category: "higiena",
                subcategory: "kosmetyki",
                amount: 33.0
            ], [
                category: "jedzenie",
                subcategory: "na mieście",
                amount: 22.32,
            ], [
                category: "higiena",
                subcategory: "kosmetyki",
                amount: 18.59
            ], [
                category: "higiena",
                subcategory: "chemia",
                amount: 19.99
            ]]

            data.each {
                def payment = new PaymentDoc()
                
                payment.setCategory(it["category"])
                payment.setSubcategory(it["subcategory"])
                payment.setAmount(it["amount"])

                paymentsList << payment
            }

            def summarizer = new PaymentSummarizer.Builder(paymentsList)
                                    .setCategoryList(categoryList)
                                    .build()

            when:
                summarizer.doSummaryForSubcategories()

            then:
                def result = summarizer.getResult()
                                .getSummaryPerSubcategory()
                
                51.59 == result["higiena"]["kosmetyki"]
                19.99 == result["higiena"]["chemia"]
                22.32 == result["jedzenie"]["na mieście"]
                0.0   == result["jedzenie"]["w pracy"]    
    }

    def "Test for summary per category"() {

        given:

            def paymentsList = []

            def data = [[
                category: "higiena",
                amount: 33.0
            ], [
                category: "jedzenie",
                amount: 22.32,
            ], [
                category: "higiena",
                amount: 18.59
            ], [
                category: "higiena",
                amount: 19.99
            ]]

            data.each {
                def payment = new PaymentDoc()
                
                payment.setCategory(it["category"])
                payment.setSubcategory(it["subcategory"])
                payment.setAmount(it["amount"])

                paymentsList << payment
            }

            def summarizer = new PaymentSummarizer.Builder(paymentsList)
                                    .setCategoryList(categoryList)
                                    .build()

            when:
                summarizer.doSummaryForCategories()

            then:
                def result = summarizer.getResult()
                                .getSummaryPerCategory()
                
                71.58 == result["higiena"]
                22.32 == result["jedzenie"] 
    }

    def "Test for all transactions summary"() {
        
        given:
            def paymentsList = []

            def data = [50.90, 5525.0, 32.57, 77.52, 75.55]

            data.each {
                def payment = new PaymentDoc()
                payment.setAmount(it)
                paymentsList << payment
            }

            def summarizer = new PaymentSummarizer.Builder(paymentsList)
                                    .build()

        when:
            summarizer.doSummaryForGlobal()

        then:
            data.sum() == summarizer.getResult().getSummaryPerPeriod()
    }

}
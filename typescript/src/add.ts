import { Calendar } from './calendar'
import { CategoryService } from './services/category.service'

import { Payment, Category } from './interfaces'

class PaymentService {
    apiUrlv1: string = 'http://localhost:8080/api/v1/payment'

    createNewPayment(payment: Payment) {

        return fetch(this.apiUrlv1, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payment)
        })
    }
}

class CategoryController {

    private categories: Array<Category> = []
    private categorySelector: HTMLSelectElement
    private subcategorySelector: HTMLSelectElement
    private amountTextor: HTMLInputElement
    private submitInput: HTMLInputElement

    private amountRegex: RegExp = new RegExp(/^[1-9]\d*$|^[1-9]\d*\.\d{1,2}$/)

    calendar = new Calendar()

    constructor(private categoryService: CategoryService, 
                private paymentService: PaymentService) { 

        this.calendar.renderCurrentDate()

        this.categorySelector = document.getElementById('category-selector')! as HTMLSelectElement
        this.subcategorySelector = document.getElementById('subcategory-selector')! as HTMLSelectElement
        this.amountTextor = document.getElementById('amount-input')! as HTMLInputElement
        this.submitInput = document.getElementById('submit-input')! as HTMLInputElement

        this.categorySelector.addEventListener('change', () => {
            
            const category: Category = this.getSelectedCategory()
            this.renderSubcategories(category.subcategories)
        })

        this.submitInput.disabled = true

        this.submitInput.addEventListener('click', () => {
            this.paymentService.createNewPayment({
                category: this.categorySelector.value,
                subcategory: this.subcategorySelector.value,
                amount: Number(this.amountTextor.value),
                date: this.calendar.getDate()
            }).then(
                () => location.href = 'http://localhost:8080/payment'
            )           
        })

        this.amountTextor.addEventListener('input', () => {
            this.submitInput.disabled = !(this.amountRegex.test(this.amountTextor.value))
        })
    }

    public getCategories(): void {

        this.categoryService.getCategories()
            .then(data => {
                this.categories = data
                
                this.renderCategories()

                const category: Category = this.getSelectedCategory()
                this.renderSubcategories(category.subcategories)
            })
    }

    public renderCategories(): void {

        this.categorySelector.innerHTML = ''

        this.categories.forEach(category => {
            const option = this.createOption(category.name, category.name)
            this.categorySelector.appendChild(option)
        })
    }

    public renderSubcategories(subcategories: Array<string>): void {

        this.subcategorySelector.innerHTML = ''
        
        subcategories.forEach(subcategory => {
            const option = this.createOption(subcategory, subcategory)
            this.subcategorySelector.appendChild(option)
        })

    }

    private createOption(value: string, text: string): HTMLOptionElement {
        let option: HTMLOptionElement = document.createElement('option')
        option.value = value
        option.text = text
        return option
    }

    private getSelectedCategory(): Category {
        return this.categories.find(el => el.name === this.categorySelector.value)!
    }
}


const categoryController = new CategoryController(new CategoryService(), new PaymentService())

categoryController.getCategories()






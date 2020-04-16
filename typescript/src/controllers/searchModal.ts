import { CategoryService } from "../services/category.service";
import { Category } from "../interfaces";
import { PaymentService } from "../services/removePayment.service";

class ModalController {

    private categoryList: Array<Category> = []

    constructor(private categoryService: CategoryService,
                private paymentService: PaymentService) { 

        document.getElementById('category-selector')
            ?.addEventListener('change', (ev) => {
                let option = (ev.target as HTMLSelectElement).value
                console.log(option)
                this.renderSubcategories(option)
            })
        
        let yearInput = document.getElementById('year-input') as HTMLInputElement
        yearInput.max = new Date().getFullYear().toString()

        document.getElementById('filter-button')
            ?.addEventListener('click', () => {

                let mydate = new Date()

                paymentService.getItems({
                    category: 'higiena',
                    subcategory: 'kosmetyki',
                    date: mydate
                })
            })
    }

    renderCategories() {

        this.categoryService.getCategories()
            .then(data => {
                this.categoryList = data
                return data
            }).then(data => {

                const categorySelector = document.getElementById('category-selector')!
                categorySelector.innerHTML = '<option text="---" value="no-value" selected hidden></option>'

                data.forEach(cat => {
                    let opt = document.createElement('option')
                    opt.text = cat.name
                    opt.value = cat.name

                    categorySelector?.appendChild(opt)
                })

            })
    }

    renderSubcategories(category: String) {

        let subcategories = this.categoryList.find(el => el.name == category)?.subcategories

        let subcategorySelector = document.getElementById('subcategory-selector')!
        subcategorySelector.innerHTML =  '<option text="---" value="no-value" selected hidden></option>'

        if (subcategories) {

            subcategories.forEach(sub => {
                let opt = document.createElement('option')
                opt.text = sub
                opt.value = sub

                subcategorySelector?.appendChild(opt)
            })

        }
    }
}

let modalController = new ModalController(new CategoryService(), new PaymentService())
modalController.renderCategories()
import { CategoryService } from "../services/category.service";
import { Category } from "../interfaces";

class ModalController {

    private categoryList: Array<Category> = []

    constructor(private categoryService: CategoryService) {

        document.getElementById('category-selector')
            ?.addEventListener('change', (ev) => {

                const option = (ev.target as HTMLSelectElement).value
                this.renderSubcategories(option)

            })
    }

    renderCategories() {

        this.categoryService.getCategories()
            .then(data => {
                this.categoryList = data
                return data
            }).then(data => {

                const categorySelector = document.getElementById('category-selector')!
                categorySelector.innerHTML = '<option text="---" value="" selected hidden></option>'

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
        subcategorySelector.innerHTML =  '<option text="---" value="" selected hidden></option>'

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

let modalController = new ModalController(new CategoryService())
modalController.renderCategories()
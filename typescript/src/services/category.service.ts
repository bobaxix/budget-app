import { Category } from './../interfaces'

export class CategoryService {

    
    apiUrlv1: string  = 'http://localhost:8080/api/v1/category'

    getCategories(): Promise<Array<Category>> {
        return fetch(this.apiUrlv1)
            .then(response => response.json())
    }
}
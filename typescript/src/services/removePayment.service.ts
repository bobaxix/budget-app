import { FilterRequest } from "../interfaces"

export class RemovePaymentService {

    apiUrlv1: string = 'http://localhost:8080/api/v1/payment'

    removeItem(id: string) {
        fetch(`${this.apiUrlv1}/${id}`, {
            method: 'DELETE'
        })
    }
}

export class PaymentService {

    apiUrlv1: string = 'http://localhost:8080/payment'

    getItems(body: FilterRequest) {

        let paramQuery = []
        let url = this.apiUrlv1

        if (body.category) {
            paramQuery.push(`category=${body.category}`)
        }

        if (body.subcategory) {
            paramQuery.push(`subcategory=${body.subcategory}`)
        }

        if (body.date) {
            paramQuery.push(`month=${body.date.getMonth() + 1}`)
            paramQuery.push(`year=${body.date.getFullYear()}`)
        }

        let fullQuery = paramQuery.join('&')
        
        if (fullQuery) {
            url += `?${fullQuery}`
        } 

        //fetch(url)
        
        location.href = url
    }
}
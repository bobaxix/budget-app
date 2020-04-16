export interface Category {
    id: string,
    name: string,
    subcategories: Array<string>
}

export interface Payment {
    category: string,
    subcategory: string,
    amount: number,
    date: string
}

export interface FilterRequest {
    category: string,
    subcategory: string,
    date: Date
}
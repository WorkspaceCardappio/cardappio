import { Category } from "./category";
import { Ingredient } from "./ingredient";
import { ProductVariable } from "./product_variable";

export interface Product {
    id: string,
    name: string,
    price: number,
    quantity: number,
    description?: string,
    active: boolean,
    category: Category,
    expirationDate: Date,    
    image?: String,
    note?: String
    parent?: Product,
    productVariables?: ProductVariable,
    ingredients: Ingredient
}
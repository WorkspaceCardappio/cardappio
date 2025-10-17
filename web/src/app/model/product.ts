import { Additional } from "./additional";
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
    expirationDate: Date, 
    image?: String,
    note?: String,
    category: Category,
    additional?: Additional[],
    productVariables?: ProductVariable[],
    ingredients: Ingredient[]    
}
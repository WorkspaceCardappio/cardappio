import { Additional } from "./additional";
import { Category } from "./category";
import { ProductItem } from "./product_item";
import { ProductVariable } from "./product_variable";

export interface Product {
    id: string,
    name: string,
    description?: string,
    expirationDate: Date, 
    image?: String,
    note?: String,
    category: Category,
    additional?: Additional[],
    productVariables?: ProductVariable[],
    productItem: ProductItem[]
}
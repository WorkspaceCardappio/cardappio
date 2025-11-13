import { Product } from "./product";
import { ProductItemIngredient } from "./product_item_ingredient";

export interface ProductItem {
    id: string,
    product: Product
    quantity: number,
    description: string,
    size: number,
    price: number,
    active: boolean,
    ingredients: ProductItemIngredient[]
}
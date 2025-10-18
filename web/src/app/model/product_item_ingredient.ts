import { Ingredient } from "./ingredient";
import { ProductItem } from "./product_item";

export interface ProductItemIngredient {
    id: string,
    item: ProductItem,
    ingredient: Ingredient,
    quantity: number
}
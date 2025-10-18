import { Ingredient } from "./ingredient";

export interface IngredientStock {
    id: string,
    ingredient: Ingredient,
    batch: string,
    quantity: number,
    expirationDate: Date
}
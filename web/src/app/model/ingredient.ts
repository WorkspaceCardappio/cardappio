import { IngredientStock } from "./ingredient-stock";

export interface Ingredient {
    id: string,
    name: string,
    active: boolean,
    quantity: number,
    expirationDate: Date,
    unityOfMeasurement: number,
    allergenic: boolean,
    stocks: IngredientStock[]
}
import { UnityOfMeasurement } from "../enum/UnityOfMeasurement";

export interface Ingredient {
    id: string,
    name: String,
    quantity: Number,
    active: Boolean,
    unityOfMeasurement: UnityOfMeasurement,
    expirationDate: Date,
    allergenic: Boolean
}
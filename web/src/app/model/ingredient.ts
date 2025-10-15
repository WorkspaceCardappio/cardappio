<<<<<<< HEAD
export interface Ingredient {
    id: string,
    name: string,
    quantity: string,
=======

export interface Ingredient {
    id: string,
    name: string,
    quantity: number,
    active: boolean,
    unityOfMeasurement: string,
>>>>>>> 33bc184af819202005f5dd006b1a718608cde299
    expirationDate: Date,
    allergenic: boolean
}
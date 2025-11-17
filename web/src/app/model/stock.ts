export interface Stock {
    id?: string;
    name?: string;
    ingredientName?: string;
    quantity: number;
    lote?: string | number;
    number?: number;
    expirationDate: Date;
    deliveryDate?: Date;
    active?: boolean;
}
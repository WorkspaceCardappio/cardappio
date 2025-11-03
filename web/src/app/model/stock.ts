export interface Stock {
    id?: string,
    name: string
    quantity: number,
    lote: string,
    expirationDate: Date,
    deliveryDate: Date,
    active?: boolean
}   
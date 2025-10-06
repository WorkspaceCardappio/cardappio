import { PaymentStatus } from "./payment-status";

export interface Payment {
    id: string,
    amount: number,
    currency: string,
    description?: string,
    status: PaymentStatus,
    paymentMethodId?: string,
    clientSecret?: string,
    created?: number
}
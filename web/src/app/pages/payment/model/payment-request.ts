export interface PaymentRequest {
    amount: number,
    currency: string,
    description?: string,
    paymentMethodId?: string,
    hcaptchaToken?: string
}
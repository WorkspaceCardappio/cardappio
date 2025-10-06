export type PaymentStatus =
    | 'requires_payment_method'
    | 'requires_confirmation'
    | 'requires_action'
    | 'processing'
    | 'succeeded'
    | 'canceled'
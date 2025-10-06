package br.com.cardappio.payment.enums;

public enum PaymentStatus {
    REQUIRES_PAYMENT_METHOD,
    REQUIRES_CONFIRMATION,
    REQUIRES_ACTION,
    PROCESSING,
    SUCCEEDED,
    CANCELED;
}

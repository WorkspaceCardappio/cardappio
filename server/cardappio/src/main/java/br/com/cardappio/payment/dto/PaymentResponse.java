package br.com.cardappio.payment.dto;

import br.com.cardappio.payment.enums.PaymentStatus;

public record PaymentResponse(
        String id,
        Long amount,
        String currency,
        PaymentStatus status,
        String description,
        String paymentMethodId,
        String clientSecret
) {
}

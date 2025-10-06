package br.com.cardappio.payment.dto;

import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PaymentRequest(
        @NotNull
        @Positive
        Long amount,

        @NotBlank
        String currency,

        String paymentMethodId,

        @Size(max = 255, message = Messages.SIZE_255)
        String description,

        String hcaptchaToken
) {}

package br.com.cardappio.payment.dto;

import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record PaymentRequestDTO(

        @NotNull(message = Messages.AMOUNT_MORE_ZERO)
        Long amount,

        @NotBlank(message = Messages.CURRENCY_DEFINITION)
        @Size(min = 3, max = 3, message = "A moeda deve ter 3 letras (ex: 'brl').")
        String currency,

        @NotNull(message = "O ID do Pedido (orderId) é obrigatório.")
        UUID orderId
) {
}
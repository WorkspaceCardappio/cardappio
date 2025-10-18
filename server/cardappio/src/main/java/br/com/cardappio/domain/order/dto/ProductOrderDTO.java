package br.com.cardappio.domain.order.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductOrderDTO(
        UUID id,
        UUID orderId,
        UUID productId,
        BigDecimal quantity,
        String note
) {
}
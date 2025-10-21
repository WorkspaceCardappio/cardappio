package br.com.cardappio.domain.order.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductAdditionalItemDTO(
        UUID id,
        String name,
        BigDecimal price
) {
}
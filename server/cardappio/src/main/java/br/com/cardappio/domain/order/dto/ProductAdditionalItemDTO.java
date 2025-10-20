package br.com.cardappio.domain.order.dto;

import java.math.BigDecimal;

public record ProductAdditionalItemDTO(
        String name,
        BigDecimal price
) {
}
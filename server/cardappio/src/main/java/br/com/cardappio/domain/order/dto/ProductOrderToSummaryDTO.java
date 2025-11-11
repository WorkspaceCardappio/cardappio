package br.com.cardappio.domain.order.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.cardappio.enums.ItemSize;

public record ProductOrderToSummaryDTO(
        UUID id,
        String productName,
        ItemSize itemSize,
        String note,
        BigDecimal quantity,
        BigDecimal price,
        BigDecimal totalPrice,
        BigDecimal variablesPrice,
        BigDecimal additionalsPrice
) {
}
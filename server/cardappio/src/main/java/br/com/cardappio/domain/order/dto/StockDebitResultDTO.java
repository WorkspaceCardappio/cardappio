package br.com.cardappio.domain.order.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Resultado de débito de estoque para um ingrediente após a finalização do pedido.
 */
public record StockDebitResultDTO(
        UUID ingredientId,
        BigDecimal newQuantity
) {}

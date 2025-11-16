package br.com.cardappio.domain.stock.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import br.com.cardappio.domain.ingredient.IngredientStock;

public record IngredientStockListDTO(
        UUID id,
        String ingredientName,
        BigDecimal quantity,
        LocalDate expirationDate,
        LocalDate deliveryDate,
        Long number
) {

    public IngredientStockListDTO(final IngredientStock ingredientStock) {
        this(
                ingredientStock.getId(),
                ingredientStock.getIngredient().getName(),
                ingredientStock.getQuantity(),
                ingredientStock.getExpirationDate(),
                ingredientStock.getDeliveryDate(),
                ingredientStock.getNumber()
        );
    }
}
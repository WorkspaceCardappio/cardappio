package br.com.cardappio.domain.product.item.dto;

import br.com.cardappio.domain.product.item.ProductItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductItemListDTO(
        UUID id,
        String product,
        BigDecimal quantity,
        BigDecimal price,
        String description,
        List<String> ingredients
) {
    public ProductItemListDTO(
            final ProductItem productItem) {
        this(
                productItem.getId(),
                productItem.getProduct().getName(),
                productItem.getQuantity(),
                productItem.getPrice(),
                productItem.getDescription(),
                productItem.getIngredients().stream()
                        .map(ingredient -> ingredient.getIngredient().getName())
                        .toList()
        );
    }
}

package br.com.cardappio.domain.product.item.dto;

import br.com.cardappio.domain.product.Product;
import br.com.cardappio.domain.product.item.ProductItemIngredient;
import br.com.cardappio.enums.ItemSize;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductItemDTO(
        UUID id,
        Product product,
        BigDecimal quantity,
        ItemSize size,
        String description,
        BigDecimal price,
        Boolean active,
        List<ProductItemIngredient> ingredients
) {

}

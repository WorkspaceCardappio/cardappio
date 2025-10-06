package br.com.cardappio.domain.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.cardappio.domain.category.Category;

public record ProductListDTO(
        UUID id,
        String name,
        BigDecimal price,
        Boolean active,
        Category category
) {
}

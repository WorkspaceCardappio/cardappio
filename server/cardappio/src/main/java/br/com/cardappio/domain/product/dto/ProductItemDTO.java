package br.com.cardappio.domain.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.cardappio.enums.ItemSize;

public record ProductItemDTO(

        UUID id,
        String size,
        BigDecimal price,
        String description,
        Boolean disable
) {

    public ProductItemDTO(final UUID id, final ItemSize size, final BigDecimal price, final String description, final Boolean active) {
        this(
                id,
                size.getDescription(),
                price,
                description,
                !active
        );
    }

}
package br.com.cardappio.domain.order.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.cardappio.enums.ItemSize;

public record ProductAdditionalDTO(

        UUID id,

        String name,

        @JsonIgnore
        String size,

        @JsonIgnore
        BigDecimal price,
        UUID itemId,

        BigDecimal quantity,

        List<ProductAdditionalItemDTO> items
) {

    public ProductAdditionalDTO(final UUID id, final String name, final ItemSize size, final BigDecimal price, final UUID item) {
        this(
                id,
                name,
                size.getDescription(),
                price,
                item,
                BigDecimal.ONE,
                new ArrayList<>()
        );
    }

}
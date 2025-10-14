package br.com.cardappio.domain.additional.dto;

import br.com.cardappio.domain.additional.Additional;
import br.com.cardappio.domain.product.Product;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record AdditionalDTO(

        UUID id,

        @NotNull
        UUID product_id,

        @NotNull
        @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
        BigDecimal price,

        String note
) {
    public AdditionalDTO(final Additional additional){
        this(
                additional.getId(),
                additional.getProduct(),
                additional.getPrice(),
                additional.getNote()
        );
    }
}

package br.com.cardappio.domain.order.dto;

import br.com.cardappio.domain.order.ProductOrderAdditional;
import br.com.cardappio.domain.order.ProductOrder;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductOrderAdditionalDTO(

        UUID id,

        @NotNull
        ProductOrder productOrder,

        BigDecimal quantity,

        BigDecimal total
) {
    public ProductOrderAdditionalDTO(final ProductOrderAdditional productOrderAdditional){
        this(
                productOrderAdditional.getId(),
                productOrderAdditional.getProductOrder(),
                productOrderAdditional.getQuantity(),
                productOrderAdditional.getTotal()
        );
    }
}

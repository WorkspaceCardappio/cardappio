package br.com.cardappio.domain.additional.dto;

import br.com.cardappio.domain.additional.Additional;
import br.com.cardappio.domain.additional.ProductOrderAdditional;
import br.com.cardappio.domain.order.ProductOrder;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductOrderAdditionalDTO(

        UUID id,

        @NotNull
        ProductOrder productOrder,

        Additional additional,

        BigDecimal quantity,

        BigDecimal total
) {
    public ProductOrderAdditionalDTO(final ProductOrderAdditional productOrderAdditional){
        this(
                productOrderAdditional.getId(),
                productOrderAdditional.getProductOrder(),
                productOrderAdditional.getAdditional(),
                productOrderAdditional.getQuantity(),
                productOrderAdditional.getTotal()
        );
    }
}

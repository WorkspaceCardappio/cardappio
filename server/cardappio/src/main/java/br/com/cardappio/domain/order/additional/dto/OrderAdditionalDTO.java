package br.com.cardappio.domain.order.additional.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.cardappio.domain.order.ProductOrderAdditional;
import br.com.cardappio.utils.IdDTO;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderAdditionalDTO(

        UUID id,

        @NotNull(message = Messages.ORDER_NOT_NULL)
        UUID order,

        @NotNull(message = Messages.PRODUCT_NOT_NULL)
        IdDTO item,

        @Min(value = 1, message = Messages.MIN_VALUE_ONE)
        @NotNull(message = Messages.QUANTITY_NOT_NULL)
        BigDecimal quantity

) {

    public OrderAdditionalDTO(final ProductOrderAdditional order) {
        this(null, order.getProductOrder().getId(), null, null);
    }

}

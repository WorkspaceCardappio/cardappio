package br.com.cardappio.domain.order.item.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.cardappio.domain.order.ProductOrder;
import br.com.cardappio.utils.IdDTO;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@SuppressWarnings("PMD")
public record OrderItemDTO(

        UUID id,

        @NotNull(message = Messages.ORDER_NOT_NULL)
        UUID order,

        @NotNull(message = Messages.PRODUCT_NOT_NULL)
        IdDTO item,

        @Min(value = 1, message = Messages.MIN_VALUE_ONE)
        @NotNull(message = Messages.QUANTITY_NOT_NULL)
        BigDecimal quantity,

        @Min(value = 1, message = Messages.MIN_VALUE_ZERO)
        @NotNull(message = Messages.EMPTY_PRICE)
        BigDecimal price

) {
    public OrderItemDTO(final ProductOrder order) {
        this(null, null, null, null, null);
    }
    //TODO: após implementação remover SuppressWarnings
}

package br.com.cardappio.domain.order.dto;

import java.util.UUID;

import br.com.cardappio.domain.order.ProductOrder;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotNull;

public record ProductOrderDTO(
        UUID id,
        @NotNull(message = Messages.ORDER_NOT_NULL)
        UUID orderId,

        @NotNull(message = Messages.PRODUCT_NOT_NULL)
        UUID productId

) {
    public ProductOrderDTO(final ProductOrder productOrder) {
        this(
                productOrder.getId(),
                productOrder.getOrder().getId(),
                productOrder.getProduct().getId()
        );
    }
}
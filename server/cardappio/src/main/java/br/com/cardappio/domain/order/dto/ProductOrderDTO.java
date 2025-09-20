package br.com.cardappio.domain.order.dto;

import java.util.UUID;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.domain.order.ProductOrder;
import br.com.cardappio.domain.product.Product;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotNull;

public record ProductOrderDTO(
        UUID id,

        @NotNull(message = Messages.ORDER_NOT_NULL)
        Order order,

        // TODO: Passar somente id
        @NotNull(message = Messages.PRODUCT_NOT_NULL)
        Product product
) {
    public ProductOrderDTO(final ProductOrder productOrder) {

        this(productOrder.getId(), productOrder.getOrder(), productOrder.getProduct());
    }
}

package br.com.cardappio.DTO;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.domain.product.Product;
import br.com.cardappio.entity.Product;
import br.com.cardappio.domain.order.ProductOrder;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

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

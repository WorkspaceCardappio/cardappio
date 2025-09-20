package br.com.cardappio.DTO;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.domain.order.ProductOrder;
import br.com.cardappio.enums.OrderStatus;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderDTO(
        UUID id,
        @NotNull(message = Messages.MIN_VALUE_ZERO)
        @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
        BigDecimal price,
        @NotNull(message = Messages.STATUS_NOT_NULL)
        OrderStatus orderStatus,
        List<ProductOrder> products
) {
    public OrderDTO(final Order order) {
        this(order.getId(), order.getPrice(), order.getStatus(), order.getProducts());
    }
}

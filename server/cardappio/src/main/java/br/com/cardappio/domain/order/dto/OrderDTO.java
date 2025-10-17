package br.com.cardappio.domain.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.enums.OrderStatus;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderDTO(
        UUID id,
        @NotNull(message = Messages.MIN_VALUE_ZERO)
        @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
        BigDecimal total,
        @NotNull(message = Messages.STATUS_NOT_NULL)
        OrderStatus orderStatus,
        List<ProductOrderDTO> products,
        @NotNull(message = Messages.TICKET_NOT_NULL)
        UUID ticketId,
        Long number,
        LocalDateTime createdAt
) {
    public OrderDTO(final Order order) {
        this(
                order.getId(),
                order.getTotal(),
                order.getStatus(),
                order.getProductOrders().stream()
                        .map(ProductOrderDTO::new)
                        .collect(Collectors.toList()),
                order.getTicket().getId(),
                order.getNumber(),
                order.getCreatedAt()
        );
    }
}
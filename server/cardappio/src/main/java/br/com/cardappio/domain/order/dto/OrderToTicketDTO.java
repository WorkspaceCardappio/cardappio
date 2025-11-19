package br.com.cardappio.domain.order.dto;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.domain.table.dto.TableRestaurantToTicketDTO;
import br.com.cardappio.utils.EnumDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderToTicketDTO(

        UUID id,
        Long number,
        EnumDTO status,
        LocalDateTime createdAt,
        BigDecimal total,
        TableRestaurantToTicketDTO table
) {

    public OrderToTicketDTO(final Order order) {
        this(
                order.getId(),
                order.getNumber(),
                order.getStatus().toDTO(),
                order.getCreatedAt(),
                order.getTotal(),
                order.getTicket() != null && order.getTicket().getTable() != null
                        ? new TableRestaurantToTicketDTO(order.getTicket().getTable())
                        : null
        );
    }

}

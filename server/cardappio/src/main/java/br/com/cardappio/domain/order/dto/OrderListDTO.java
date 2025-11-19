package br.com.cardappio.domain.order.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.domain.ticket.dto.TicketListDTO;
import br.com.cardappio.utils.EnumDTO;

public record OrderListDTO(

        UUID id,
        Long number,
        EnumDTO status,
        TicketListDTO ticket,
        LocalDateTime createdAt,
        String createdBy
) {

    public OrderListDTO(final Order order) {
        this(
                order.getId(),
                order.getNumber(),
                order.getStatus().toDTO(),
                new TicketListDTO(order.getTicket()),
                order.getCreatedAt(),
                order.getCreatedBy() != null ? order.getCreatedBy() : "Sistema"
        );
    }

}
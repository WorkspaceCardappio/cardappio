package br.com.cardappio.domain.order.dto;

import java.util.UUID;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.domain.ticket.dto.TicketListDTO;
import br.com.cardappio.utils.EnumDTO;

public record OrderListDTO(

        UUID id,

        EnumDTO status,

        TicketListDTO ticket
) {

    public OrderListDTO(final Order order) {
        this(
                order.getId(),
                order.getStatus().toDTO(),
                new TicketListDTO(order.getTicket())
        );
    }

}
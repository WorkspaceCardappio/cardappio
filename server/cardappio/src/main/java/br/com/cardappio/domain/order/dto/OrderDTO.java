package br.com.cardappio.domain.order.dto;

import java.util.UUID;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.utils.EnumDTO;
import br.com.cardappio.utils.IdDTO;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotNull;

public record OrderDTO(

        UUID id,

        @NotNull(message = Messages.STATUS_NOT_NULL)
        EnumDTO status,

        @NotNull(message = Messages.TICKET_NOT_NULL)
        IdDTO ticket
) {
    public OrderDTO(final Order order) {
        this(
                order.getId(),
                order.getStatus().toDTO(),
                new IdDTO(order.getTicket().getId())
        );
    }
}
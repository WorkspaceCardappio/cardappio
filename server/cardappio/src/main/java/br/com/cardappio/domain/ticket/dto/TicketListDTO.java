package br.com.cardappio.domain.ticket.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.cardappio.domain.ticket.Ticket;
import br.com.cardappio.utils.EnumDTO;

public record TicketListDTO(

        UUID id,

        Long number,

        BigDecimal total,

        EnumDTO status,

        String person,

        String table
) {
    public TicketListDTO(final Ticket ticket){
        this(
                ticket.getId(),
                ticket.getNumber(),
                ticket.getTotal(),
                ticket.getStatus().toDTO(),
                ticket.getOwner().getName(),
                ticket.getTable().getNumber()
        );
    }
}

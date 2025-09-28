package br.com.cardappio.domain.ticket.dto;

import java.util.UUID;

import br.com.cardappio.domain.ticket.Ticket;

public record TicketListDTO(

        UUID id,

        String number,

        Long code,

        String person,

        String table
) {
    public TicketListDTO(final Ticket ticket){
        this(
                ticket.getId(),
                ticket.getNumber(),
                ticket.getStatus().getCode(),
                ticket.getOwner().getName(),
                ticket.getTable().getNumber()
        );
    }
}

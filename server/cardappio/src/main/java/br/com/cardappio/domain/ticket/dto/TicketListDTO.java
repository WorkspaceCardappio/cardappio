package br.com.cardappio.domain.ticket.dto;

import java.util.UUID;

import br.com.cardappio.domain.table.dto.TableRestaurantToTicketDTO;
import br.com.cardappio.domain.ticket.Ticket;
import br.com.cardappio.utils.EnumDTO;

public record TicketListDTO(

        UUID id,

        Long number,

        EnumDTO status,

        String person,

        TableRestaurantToTicketDTO table
) {
    public TicketListDTO(final Ticket ticket) {
        this(
                ticket.getId(),
                ticket.getNumber(),
                ticket.getStatus().toDTO(),
                ticket.getCreatedBy() != null ? ticket.getCreatedBy() : "Sistema",
                new TableRestaurantToTicketDTO(ticket.getTable())
        );
    }
}

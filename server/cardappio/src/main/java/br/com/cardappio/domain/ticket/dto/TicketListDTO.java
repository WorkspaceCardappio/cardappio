package br.com.cardappio.domain.ticket.dto;

import java.util.Optional;
import java.util.UUID;

import br.com.cardappio.domain.person.Person;
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
                Optional.ofNullable(ticket.getOwner()).map(Person::getName).orElse("Darth Vader"),
                new TableRestaurantToTicketDTO(ticket.getTable())
        );
    }
}

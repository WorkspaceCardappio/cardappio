package br.com.cardappio.domain.ticket.dto;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.domain.person.Person;
import br.com.cardappio.domain.table.dto.TableRestaurantToTicketDTO;
import br.com.cardappio.domain.ticket.Ticket;
import br.com.cardappio.utils.EnumDTO;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public record TicketListDTO(

        UUID id,

        Long number,

        EnumDTO status,

        String person,

        TableRestaurantToTicketDTO table,

        BigDecimal total
) {
    public TicketListDTO(final Ticket ticket) {
        this(
                ticket.getId(),
                ticket.getNumber(),
                ticket.getStatus().toDTO(),
                Optional.ofNullable(ticket.getOwner()).map(Person::getName).orElse("Darth Vader"),
                new TableRestaurantToTicketDTO(ticket.getTable()),
                ticket.getOrders().stream().map(Order::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }
}

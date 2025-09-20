package br.com.cardappio.domain.ticket.adapter;

import com.cardappio.core.adapter.Adapter;

import br.com.cardappio.domain.ticket.dto.TicketDTO;
import br.com.cardappio.domain.ticket.Ticket;

public class TicketAdapter implements Adapter<Ticket, TicketDTO, TicketDTO> {

    @Override
    public TicketDTO toDTO(final Ticket entity) {
        return new TicketDTO(entity);
    }

    @Override
    public Ticket toEntity(final TicketDTO dto) {
        return Ticket.of(dto);
    }
}

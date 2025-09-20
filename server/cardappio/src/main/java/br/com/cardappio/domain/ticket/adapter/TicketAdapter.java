package br.com.cardappio.domain.ticket.adapter;

import com.cardappio.core.adapter.Adapter;

import br.com.cardappio.domain.ticket.dto.TicketDTO;
import br.com.cardappio.domain.ticket.Ticket;
import br.com.cardappio.domain.ticket.dto.TicketListDTO;

public class TicketAdapter implements Adapter<Ticket, TicketListDTO, TicketDTO> {

    @Override
    public TicketListDTO toDTO(final Ticket entity) {
        return new TicketListDTO(entity);
    }

    @Override
    public Ticket toEntity(final TicketDTO dto) {
        return Ticket.of(dto);
    }
}

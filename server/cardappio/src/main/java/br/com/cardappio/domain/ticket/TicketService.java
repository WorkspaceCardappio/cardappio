package br.com.cardappio.domain.ticket;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import br.com.cardappio.domain.ticket.dto.TicketDTO;
import br.com.cardappio.domain.ticket.adapter.TicketAdapter;

@Service
public class TicketService extends CrudService<Ticket, UUID, TicketDTO, TicketDTO> {

    @Override
    protected Adapter<Ticket, TicketDTO, TicketDTO> getAdapter() {
        return new TicketAdapter();
    }
}

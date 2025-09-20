package br.com.cardappio.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import br.com.cardappio.DTO.TicketDTO;
import br.com.cardappio.adapter.TicketAdapter;
import br.com.cardappio.entity.Ticket;

@Service
public class TicketService extends CrudService<Ticket, UUID, TicketDTO, TicketDTO> {

    @Override
    protected Adapter<Ticket, TicketDTO, TicketDTO> getAdapter() {
        return new TicketAdapter();
    }
}

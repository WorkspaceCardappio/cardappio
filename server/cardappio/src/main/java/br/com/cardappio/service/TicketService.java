package br.com.cardappio.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import br.com.cardappio.DTO.TicketDTO;
import br.com.cardappio.adapter.TicketAdapter;
import br.com.cardappio.entity.Ticket;

@Service
public class TicketService extends CrudService<Ticket, TicketDTO, UUID> {

    @Override
    protected Adapter<TicketDTO, Ticket> getAdapter() {
        return new TicketAdapter();
    }
}

package br.com.cardappio.domain.ticket;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardappio.core.controller.CrudController;

import br.com.cardappio.domain.ticket.dto.TicketDTO;
import br.com.cardappio.domain.ticket.dto.TicketListDTO;

@RestController
@RequestMapping("/tickets")
public class TicketController extends CrudController<Ticket, UUID, TicketListDTO, TicketDTO> {
}

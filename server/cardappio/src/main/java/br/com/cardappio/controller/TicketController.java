package br.com.cardappio.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardappio.core.controller.CrudController;

import br.com.cardappio.DTO.TicketDTO;
import br.com.cardappio.entity.Ticket;

@RestController
@RequestMapping("/tickets")
public class TicketController extends CrudController<Ticket, UUID, TicketDTO, TicketDTO> {
}

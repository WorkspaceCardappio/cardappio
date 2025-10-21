package br.com.cardappio.domain.ticket;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardappio.core.controller.CrudController;

import br.com.cardappio.domain.ticket.dto.TicketDTO;
import br.com.cardappio.domain.ticket.dto.TicketListDTO;
import br.com.cardappio.domain.ticket.split.SplitService;
import br.com.cardappio.domain.ticket.split.dto.SplitOrdersDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class TicketController extends CrudController<Ticket, UUID, TicketListDTO, TicketDTO> {

    // TODO: REMOVER - VIRAR USUARIO LOGADO
    private final UUID idPerson = UUID.fromString("0ad8e87d-a9db-4746-823d-eeb7cd0efb10");

    private final SplitService splitService;

    @PostMapping("/split/{id}")
    public ResponseEntity<Void> split(@PathVariable final UUID id, @RequestBody @Valid final SplitOrdersDTO bodySplit) {
        splitService.ticket(id, idPerson, bodySplit);
        return ResponseEntity.ok().build();
    }

}

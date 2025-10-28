package br.com.cardappio.domain.ticket;

import br.com.cardappio.domain.ticket.dto.FlutterTicketDTO;
import br.com.cardappio.domain.ticket.dto.TicketDTO;
import br.com.cardappio.domain.ticket.dto.TicketListDTO;
import br.com.cardappio.domain.ticket.split.SplitService;
import br.com.cardappio.domain.ticket.split.dto.SplitOrdersDTO;
import com.cardappio.core.controller.CrudController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class TicketController extends CrudController<Ticket, UUID, TicketListDTO, TicketDTO> {

    private final TicketService service;

    // TODO: REMOVER - VIRAR USUARIO LOGADO
    private final UUID idPerson = UUID.fromString("0ad8e87d-a9db-4746-823d-eeb7cd0efb10");

    private final SplitService splitService;

    @PostMapping("/split/{id}")
    public ResponseEntity<Void> split(@PathVariable final UUID id, @RequestBody @Valid final SplitOrdersDTO bodySplit) {
        splitService.ticket(id, idPerson, bodySplit);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{idTicket}/flutter-tickets")
    public ResponseEntity<FlutterTicketDTO> findFlutterTicket(@PathVariable UUID idTicket) {

        return ResponseEntity.ok(service.findFlutterTicket(idTicket));
    }

}

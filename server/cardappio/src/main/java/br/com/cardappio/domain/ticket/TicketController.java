package br.com.cardappio.domain.ticket;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardappio.core.controller.CrudController;

import br.com.cardappio.domain.order.dto.IdsDTO;
import br.com.cardappio.domain.ticket.dto.FlutterTicketDTO;
import br.com.cardappio.domain.ticket.dto.TicketDTO;
import br.com.cardappio.domain.ticket.dto.TicketListDTO;
import br.com.cardappio.domain.ticket.dto.TotalAndIdDTO;
import br.com.cardappio.domain.ticket.split.SplitService;
import br.com.cardappio.domain.ticket.split.dto.SplitOrdersDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class TicketController extends CrudController<Ticket, UUID, TicketListDTO, TicketDTO> {

    private final TicketService service;

    // TODO: REMOVER - VIRAR USUARIO LOGADO
    private final UUID idPerson = UUID.fromString("a4b5c6d7-e1f2-3456-7890-123456abcdef");

    private final SplitService splitService;

    @PostMapping("/split/{id}")
    public ResponseEntity<Void> split(@PathVariable final UUID id, @RequestBody @Valid final SplitOrdersDTO bodySplit) {
        splitService.ticket(id, idPerson, bodySplit);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/flutter-tickets/by-ticket/{idTicket}")
    public ResponseEntity<FlutterTicketDTO> findFlutterTicket(@PathVariable UUID idTicket) {
        return ResponseEntity.ok(service.findFlutterTicket(idTicket));
    }

    @PostMapping("/total-by-ids")
    public List<TotalAndIdDTO> totalByIds(@RequestBody @Valid IdsDTO ids) {
        return service.getTotalByids(ids);
    }

}

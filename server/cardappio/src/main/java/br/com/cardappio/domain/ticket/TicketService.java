package br.com.cardappio.domain.ticket;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import br.com.cardappio.config.security.SecurityUtils;
import br.com.cardappio.domain.order.dto.FlutterOrderDTO;
import br.com.cardappio.domain.order.dto.IdsDTO;
import br.com.cardappio.domain.ticket.adapter.TicketAdapter;
import br.com.cardappio.domain.ticket.dto.FlutterTicketDTO;
import br.com.cardappio.domain.ticket.dto.TicketDTO;
import br.com.cardappio.domain.ticket.dto.TicketListDTO;
import br.com.cardappio.domain.ticket.dto.TotalAndIdDTO;
import br.com.cardappio.websocket.EventType;
import br.com.cardappio.websocket.WebSocketNotificationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService extends CrudService<Ticket, UUID, TicketListDTO, TicketDTO> {

    private final TicketRepository repository;
    private final WebSocketNotificationService webSocketNotificationService;

    @Override
    protected Adapter<Ticket, TicketListDTO, TicketDTO> getAdapter() {
        return new TicketAdapter();
    }

    @Override
    public UUID create(final TicketDTO dto) {
        final Ticket ticket = getAdapter().toEntity(dto);
        ticket.setCreatedBy(SecurityUtils.getUserIdentifier());
        UUID ticketId = repository.save(ticket).getId();
        webSocketNotificationService.notifyTicketChange(ticketId.toString(), EventType.CREATED, null);
        return ticketId;
    }

    public FlutterTicketDTO findFlutterTicket(UUID idTicket) {

        Ticket ticketEntity = repository.findByIdWithOrdersFlutter(idTicket)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

        FlutterTicketDTO ticketDTO = new FlutterTicketDTO();

        List<FlutterOrderDTO> aggregatedOrderItems = ticketEntity.getOrders().stream()
                .flatMap(order -> order.getProductOrders().stream())
                .map(productOrder -> new FlutterOrderDTO(
                        productOrder.getOrder().getId(),
                        productOrder.getProductItem().getProduct().getName(),
                        productOrder.getPrice(),
                        productOrder.getQuantity().longValue()

                ))
                .toList();

        ticketDTO.getOrders().addAll(aggregatedOrderItems);

        return ticketDTO;
    }

    public List<TotalAndIdDTO> getTotalByids(final IdsDTO body) {

        if (body.ids().isEmpty()) {
            return List.of();
        }

        return repository.findTotalByIds(body.ids());
    }

}

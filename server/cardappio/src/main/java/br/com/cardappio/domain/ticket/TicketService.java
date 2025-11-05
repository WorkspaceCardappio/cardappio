package br.com.cardappio.domain.ticket;

import br.com.cardappio.domain.order.OrderRepository;
import br.com.cardappio.domain.order.dto.FlutterOrderDTO;
import br.com.cardappio.domain.ticket.adapter.TicketAdapter;
import br.com.cardappio.domain.ticket.dto.FlutterTicketDTO;
import br.com.cardappio.domain.ticket.dto.TicketDTO;
import br.com.cardappio.domain.ticket.dto.TicketListDTO;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService extends CrudService<Ticket, UUID, TicketListDTO, TicketDTO> {

    private final TicketRepository repository;

    private final OrderRepository orderRepository;

    @Override
    protected Adapter<Ticket, TicketListDTO, TicketDTO> getAdapter() {
        return new TicketAdapter();
    }

    public FlutterTicketDTO findFlutterTicket(UUID idTicket) {

        Ticket ticketEntity = repository.findByIdWithOrders(idTicket)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

        FlutterTicketDTO ticketDTO = new FlutterTicketDTO(ticketEntity.getTotal());

        List<FlutterOrderDTO> aggregatedOrderItems = ticketEntity.getOrders().stream()

                .flatMap(order -> order.getProductOrders().stream())
                .map(productOrder -> new FlutterOrderDTO(

                        productOrder.getProductItem().getProduct().getName(),
                        productOrder.getPrice(),
                        productOrder.getQuantity().longValue()

                ))
                .toList();


        ticketDTO.getOrders().addAll(aggregatedOrderItems);

        return ticketDTO;
    }

}

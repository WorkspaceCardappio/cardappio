package br.com.cardappio.domain.order;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import br.com.cardappio.domain.order.adapter.OrderAdapter;
import br.com.cardappio.domain.order.dto.OrderDTO;
import br.com.cardappio.domain.order.dto.OrderToTicketDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService extends CrudService<Order, UUID, OrderDTO, OrderDTO> {

    private final OrderRepository repository;

    @Override
    protected Adapter<Order, OrderDTO, OrderDTO> getAdapter() {
        return new OrderAdapter();
    }

    public Page<OrderToTicketDTO> findToTicket(final UUID id, final Pageable pageable) {
        return repository.findByTicketId(id, pageable)
                .map(OrderToTicketDTO::new);
    }
}

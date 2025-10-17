package br.com.cardappio.domain.order;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardappio.core.controller.CrudController;

import br.com.cardappio.domain.order.dto.OrderDTO;
import br.com.cardappio.domain.order.dto.OrderToTicketDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController extends CrudController<Order, UUID, OrderDTO, OrderDTO> {

    private final OrderService service;

    @GetMapping("/to-ticket/{id}")
    public Page<OrderToTicketDTO> findToTicket(@PathVariable final UUID id,
            @PageableDefault(size = 20) final Pageable pageable) {

        return service.findToTicket(id, pageable);
    }

}

package br.com.cardappio.domain.order;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import br.com.cardappio.domain.order.adapter.OrderAdapter;
import br.com.cardappio.domain.order.dto.OrderDTO;

@Service
public class OrderService extends CrudService<Order, UUID, OrderDTO, OrderDTO> {

    @Override
    protected Adapter<Order, OrderDTO, OrderDTO> getAdapter() {

        return new OrderAdapter();
    }
}

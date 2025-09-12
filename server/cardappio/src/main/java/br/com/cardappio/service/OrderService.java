package br.com.cardappio.service;

import br.com.cardappio.DTO.OrderDTO;
import br.com.cardappio.adapter.OrderAdapter;
import br.com.cardappio.entity.Order;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService extends CrudService<Order, OrderDTO, UUID> {

    @Override
    protected Adapter<OrderDTO, Order> getAdapter() {

        return new OrderAdapter();
    }
}

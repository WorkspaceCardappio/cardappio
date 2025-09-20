package br.com.cardappio.domain.order.adapter;

import com.cardappio.core.adapter.Adapter;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.domain.order.dto.OrderDTO;

public class OrderAdapter implements Adapter<Order, OrderDTO, OrderDTO> {

    @Override
    public OrderDTO toDTO(final Order entity) {
        return new OrderDTO(entity);
    }

    @Override
    public Order toEntity(final OrderDTO dto) {
        return Order.of(dto);
    }
}

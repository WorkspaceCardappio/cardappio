package br.com.cardappio.domain.order.adapter;

import com.cardappio.core.adapter.Adapter;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.domain.order.dto.OrderDTO;
import br.com.cardappio.domain.order.dto.OrderListDTO;

public class OrderAdapter implements Adapter<Order, OrderListDTO, OrderDTO> {

    @Override
    public OrderListDTO toDTO(final Order entity) {
        return new OrderListDTO(entity);
    }

    @Override
    public Order toEntity(final OrderDTO dto) {
        return Order.of(dto);
    }
}

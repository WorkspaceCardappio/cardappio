package br.com.cardappio.adapter;

import br.com.cardappio.DTO.OrderDTO;
import br.com.cardappio.domain.order.Order;
import com.cardappio.core.adapter.Adapter;

public class OrderAdapter implements Adapter<OrderDTO, Order> {

    @Override
    public OrderDTO toDTO(final Order entity) {
        return new OrderDTO(entity);
    }

    @Override
    public Order toEntity(final OrderDTO dto) {
        return Order.of(dto);
    }
}

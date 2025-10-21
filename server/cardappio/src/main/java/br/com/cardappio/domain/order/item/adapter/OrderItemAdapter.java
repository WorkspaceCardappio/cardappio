package br.com.cardappio.domain.order.item.adapter;

import com.cardappio.core.adapter.Adapter;

import br.com.cardappio.domain.order.ProductOrder;
import br.com.cardappio.domain.order.item.dto.OrderItemDTO;

public class OrderItemAdapter implements Adapter<ProductOrder, OrderItemDTO, OrderItemDTO> {

    @Override
    public OrderItemDTO toDTO(final ProductOrder entity) {
        return new OrderItemDTO(entity);
    }

    @Override
    public ProductOrder toEntity(final OrderItemDTO dto) {
        return ProductOrder.of(dto);
    }
}

package br.com.cardappio.domain.order.additional.adapter;

import com.cardappio.core.adapter.Adapter;

import br.com.cardappio.domain.order.ProductOrderAdditional;
import br.com.cardappio.domain.order.additional.dto.OrderAdditionalDTO;

public class OrderAdditionalAdapter implements Adapter<ProductOrderAdditional, OrderAdditionalDTO, OrderAdditionalDTO> {

    @Override
    public OrderAdditionalDTO toDTO(final ProductOrderAdditional entity) {
        return new OrderAdditionalDTO(entity);
    }

    @Override
    public ProductOrderAdditional toEntity(final OrderAdditionalDTO dto) {
        return ProductOrderAdditional.of(dto);
    }
}

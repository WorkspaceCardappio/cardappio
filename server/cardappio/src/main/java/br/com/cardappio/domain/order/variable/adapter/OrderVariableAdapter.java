package br.com.cardappio.domain.order.variable.adapter;

import com.cardappio.core.adapter.Adapter;

import br.com.cardappio.domain.order.ProductOrderVariable;
import br.com.cardappio.domain.order.variable.dto.OrderVariableDTO;

public class OrderVariableAdapter implements Adapter<ProductOrderVariable, OrderVariableDTO, OrderVariableDTO> {

    @Override
    public OrderVariableDTO toDTO(final ProductOrderVariable entity) {
        return new OrderVariableDTO(entity);
    }

    @Override
    public ProductOrderVariable toEntity(final OrderVariableDTO dto) {
        return ProductOrderVariable.of(dto);
    }
}

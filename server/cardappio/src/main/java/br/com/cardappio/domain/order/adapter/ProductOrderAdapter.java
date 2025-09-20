package br.com.cardappio.domain.order.adapter;

import com.cardappio.core.adapter.Adapter;

import br.com.cardappio.domain.order.ProductOrder;
import br.com.cardappio.domain.order.dto.ProductOrderDTO;

public class ProductOrderAdapter implements Adapter<ProductOrder, ProductOrderDTO, ProductOrderDTO> {

    @Override
    public ProductOrderDTO toDTO(final ProductOrder entity) {
        return new ProductOrderDTO(entity);
    }

    @Override
    public ProductOrder toEntity(final ProductOrderDTO dto) {
        return ProductOrder.of(dto);
    }
}

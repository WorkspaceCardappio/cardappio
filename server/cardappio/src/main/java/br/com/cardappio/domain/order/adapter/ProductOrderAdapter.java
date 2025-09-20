package br.com.cardappio.adapter;

import br.com.cardappio.DTO.ProductOrderDTO;
import br.com.cardappio.domain.order.ProductOrder;
import com.cardappio.core.adapter.Adapter;

public class ProductOrderAdapter implements Adapter<ProductOrderDTO, ProductOrder> {

    @Override
    public ProductOrderDTO toDTO(final ProductOrder entity) {
        return new ProductOrderDTO(entity);
    }

    @Override
    public ProductOrder toEntity(final ProductOrderDTO dto) {
        return ProductOrder.of(dto);
    }
}

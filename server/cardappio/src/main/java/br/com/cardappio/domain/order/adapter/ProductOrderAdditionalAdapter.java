package br.com.cardappio.domain.order.adapter;

import br.com.cardappio.domain.order.ProductOrder;
import br.com.cardappio.domain.order.ProductOrderAdditional;
import br.com.cardappio.domain.order.dto.ProductOrderAdditionalDTO;
import br.com.cardappio.domain.order.dto.ProductOrderDTO;
import com.cardappio.core.adapter.Adapter;

public class ProductOrderAdditionalAdapter implements Adapter<ProductOrderAdditional, ProductOrderAdditionalDTO, ProductOrderAdditionalDTO> {

    @Override
    public ProductOrderAdditionalDTO toDTO(final ProductOrderAdditional entity) {
        return new ProductOrderAdditionalDTO(entity);
    }

    @Override
    public ProductOrderAdditional toEntity(final ProductOrderAdditionalDTO dto) {
        return ProductOrderAdditional.of(dto);
    }
}

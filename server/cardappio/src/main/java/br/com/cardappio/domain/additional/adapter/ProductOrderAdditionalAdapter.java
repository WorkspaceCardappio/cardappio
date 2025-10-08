package br.com.cardappio.domain.additional.adapter;

import br.com.cardappio.domain.additional.ProductOrderAdditional;
import br.com.cardappio.domain.additional.dto.ProductOrderAdditionalDTO;
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

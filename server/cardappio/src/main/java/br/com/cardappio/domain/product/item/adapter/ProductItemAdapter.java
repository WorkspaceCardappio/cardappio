package br.com.cardappio.domain.product.item.adapter;

import br.com.cardappio.domain.product.item.ProductItem;
import br.com.cardappio.domain.product.item.dto.ProductItemDTO;
import br.com.cardappio.domain.product.item.dto.ProductItemListDTO;
import com.cardappio.core.adapter.Adapter;

public class ProductItemAdapter implements Adapter<ProductItem, ProductItemListDTO, ProductItemDTO> {
    @Override
    public ProductItemListDTO toDTO(ProductItem entity) {
        return new ProductItemListDTO(entity);
    }

    @Override
    public ProductItem toEntity(ProductItemDTO dto) {
        return ProductItem.of(dto);
    }
}

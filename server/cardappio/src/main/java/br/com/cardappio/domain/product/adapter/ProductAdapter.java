package br.com.cardappio.domain.product.adapter;

import br.com.cardappio.domain.product.dto.ProductDTO;
import br.com.cardappio.domain.product.Product;
import com.cardappio.core.adapter.Adapter;

public class ProductAdapter implements Adapter<Product, ProductDTO, ProductDTO> {
    @Override
    public ProductDTO toDTO(final Product entity) {
        return new ProductDTO(entity);
    }

    @Override
    public Product toEntity(final ProductDTO dto) {
        return Product.of(dto);
    }
}

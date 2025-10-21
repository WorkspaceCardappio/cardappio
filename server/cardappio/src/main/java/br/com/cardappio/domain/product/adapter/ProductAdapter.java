package br.com.cardappio.domain.product.adapter;

import br.com.cardappio.domain.product.dto.ProductDTO;
import br.com.cardappio.domain.product.Product;
import br.com.cardappio.domain.product.dto.ProductListDTO;
import com.cardappio.core.adapter.Adapter;

public class ProductAdapter implements Adapter<Product, ProductListDTO, ProductDTO> {
    @Override
    public ProductListDTO toDTO(final Product entity) {
        return new ProductListDTO(entity);
    }

    @Override
    public Product toEntity(final ProductDTO dto) {
        return Product.of(dto);
    }
}

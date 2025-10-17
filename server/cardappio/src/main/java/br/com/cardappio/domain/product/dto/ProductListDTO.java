package br.com.cardappio.domain.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.cardappio.domain.product.Product;

public record ProductListDTO(
        UUID id,
        String name,
        String category,
        BigDecimal price,
        Boolean active

) {
    public ProductListDTO(final Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getCategory().getName(),
                product.getPrice(),
                product.getActive()
        );
    }
}

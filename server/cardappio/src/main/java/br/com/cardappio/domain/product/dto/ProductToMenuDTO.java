package br.com.cardappio.domain.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.cardappio.domain.product.Product;

public record ProductToMenuDTO(

        UUID id,
        String name,
        BigDecimal price,
        Boolean active
) {

    public ProductToMenuDTO(final Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getActive()
        );
    }
}

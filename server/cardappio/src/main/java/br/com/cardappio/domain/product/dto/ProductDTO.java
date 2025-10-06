package br.com.cardappio.domain.product.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import br.com.cardappio.domain.category.Category;
import br.com.cardappio.domain.product.Product;

public record ProductDTO(
        UUID id,
        String name,
        BigDecimal price,
        BigDecimal quantity,
        Boolean active,
        LocalDate expirationDate,
        String image,
        Category category
) {
    public ProductDTO(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getActive(),
                product.getExpirationDate(),
                product.getImage(),
                product.getCategory()
        );
    }
}

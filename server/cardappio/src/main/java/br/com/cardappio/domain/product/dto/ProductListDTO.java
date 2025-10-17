package br.com.cardappio.domain.product.dto;

import br.com.cardappio.domain.category.Category;
import br.com.cardappio.domain.product.Product;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductListDTO(
        UUID id,
        String name,
        String category,
        BigDecimal price,
        Boolean active

) {
    public ProductListDTO(final Product product){
        this(
                product.getId(),
                product.getName(),
                product.getCategory().getName(),
                product.getPrice(),
                product.getActive()
        );
    }
}

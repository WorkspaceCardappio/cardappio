package br.com.cardappio.domain.product.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import br.com.cardappio.domain.category.Category;
import br.com.cardappio.domain.product.Product;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

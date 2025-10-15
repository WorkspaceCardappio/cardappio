package br.com.cardappio.domain.product.dto;

import br.com.cardappio.domain.category.Category;
import br.com.cardappio.domain.product.Product;
import br.com.cardappio.utils.Messages;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ProductDTO(

        UUID id,

        @NotBlank(message = Messages.EMPTY_NAME)
        @Length(max = 255, message = Messages.SIZE_255)
        String name,

        @NotNull
        @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
        BigDecimal price,

        @NotNull
        @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
        BigDecimal quantity,

        @Length(max = 255, message = Messages.SIZE_255)
        String description,

        Boolean active,

        Category category,

        @Future
        LocalDate expirationDate,

        @Length(max = 255, message = Messages.SIZE_255)
        String image,

        @Length(max = 255, message = Messages.SIZE_255)
        String note
) {

    public ProductDTO(final Product product){
        this(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getDescription(),
                product.getActive(),
                product.getCategory(),
                product.getExpirationDate(),
                product.getImage(),
                product.getNote()
        );
    }
}

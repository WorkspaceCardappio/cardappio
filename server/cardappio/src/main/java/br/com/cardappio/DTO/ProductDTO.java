package br.com.cardappio.DTO;

import br.com.cardappio.entity.Category;
import br.com.cardappio.entity.Product;
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

        Boolean active,

        @Future
        LocalDate expirationDate,

        @Length(max = 255, message = Messages.SIZE_255)
        String image,

        @NotNull
        Category category
) {

    public ProductDTO(final Product product){
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

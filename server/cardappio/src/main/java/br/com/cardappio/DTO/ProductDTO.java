package br.com.cardappio.DTO;

import br.com.cardappio.entity.Category;
import br.com.cardappio.entity.Product;
import br.com.cardappio.entity.ProductIngredient;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record ProductDTO(

        UUID id,

        @NotBlank(message = Messages.EMPTY_NAME)
        @Length(max = 255, message = Messages.SIZE_255)
        String name,

        @NotNull
        BigDecimal price,

        @NotNull
        BigDecimal quantity,

        @NotNull
        LocalDate dataVencimento,

        boolean active,

        @NotNull
        Category category,

        @Length(max = 255, message = Messages.SIZE_255)
        String image,

        @NotNull
        List<ProductIngredient> productIngredient
) {

    public ProductDTO(final Product product){
        this(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getDataVencimento(),
                product.isActive(),
                product.getCategory(),
                product.getImage(),
                product.getProductIngredient()
        );
    }

}

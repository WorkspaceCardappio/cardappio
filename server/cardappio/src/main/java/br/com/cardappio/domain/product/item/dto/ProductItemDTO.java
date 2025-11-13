package br.com.cardappio.domain.product.item.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductItemDTO(

        UUID id,

        @NotNull(message = Messages.PRODUCT_NOT_NULL)
        UUID product,

        @NotNull(message = Messages.QUANTITY_NOT_NULL)
        @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
        BigDecimal quantity,

        @NotNull(message = Messages.SIZE_INGREDIENT)
        Long size,

        String description,

        @NotNull
        @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
        BigDecimal price,

        Boolean active,

        List<ProductItemIngredientDTO> ingredients
) {

}

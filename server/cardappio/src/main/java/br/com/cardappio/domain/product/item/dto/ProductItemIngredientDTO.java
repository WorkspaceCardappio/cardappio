package br.com.cardappio.domain.product.item.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.cardappio.utils.IdDTO;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductItemIngredientDTO(

        UUID id,
        
        @NotNull(message = Messages.INGREDIENT_NOT_NULL)
        IdDTO ingredient,

        @NotNull(message = Messages.QUANTITY_NOT_NULL)
        @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
        BigDecimal quantity
) {
}

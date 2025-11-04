package br.com.cardappio.domain.ingredient.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import br.com.cardappio.utils.IdDTO;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record IngredientStockDTO(
        UUID id,
        IdDTO ingredient,

        @NotNull(message = Messages.EMPTY_NUMBER)
        @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
        Long number,

        @NotNull(message = Messages.QUANTITY_NOT_NULL)
        @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
        BigDecimal quantity,

        @NotNull(message = Messages.EXPIRATION_DATE_NOT_NULL)
        @Future(message = Messages.EXPIRATION_DATE_NOT_PAST)
        LocalDate expirationDate

) {
}

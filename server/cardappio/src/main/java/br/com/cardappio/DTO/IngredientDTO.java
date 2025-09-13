package br.com.cardappio.DTO;

import br.com.cardappio.entity.Ingredient;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record IngredientDTO(
        UUID id,
        @NotBlank(message = Messages.EMPTY_NAME)
        @Length(max = 255, message = Messages.SIZE_255)
        String name,
        @NotNull
        @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
        BigDecimal quantity,
        @NotNull
        LocalDate expirationDate,
        @NotNull
        Boolean allergenic
) {
    public IngredientDTO(final Ingredient ingredient) {
        this(ingredient.getId(), ingredient.getName(), ingredient.getQuantity(), ingredient.getExpirationDate(), ingredient.getAllergenic());
    }
}


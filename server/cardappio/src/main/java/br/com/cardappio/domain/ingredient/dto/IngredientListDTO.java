package br.com.cardappio.domain.ingredient.dto;

import br.com.cardappio.domain.ingredient.Ingredient;
import br.com.cardappio.utils.EnumDTO;

import java.math.BigDecimal;
import java.util.UUID;

public record IngredientListDTO(
        UUID id,
        String name,
        EnumDTO unityOfMeasurement,
        BigDecimal quantity,
        boolean active
) {
    public IngredientListDTO(final Ingredient ingredient) {
        this(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getUnityOfMeasurement().toDTO(),
                ingredient.getQuantity(),
                ingredient.getActive()
        );
    }
}

package br.com.cardappio.domain.ingredient.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.cardappio.domain.ingredient.Ingredient;

public record IngredientListDTO(
        UUID id,
        String name,
        UnityOfMeasurementDTO unityOfMeasurement,
        BigDecimal quantity,
        boolean active,
        boolean allergenic
) {
    public IngredientListDTO(final Ingredient ingredient) {
        this(
                ingredient.getId(),
                ingredient.getName(),
                new UnityOfMeasurementDTO(ingredient.getUnityOfMeasurement()),
                ingredient.getQuantity(),
                ingredient.getActive(),
                ingredient.getAllergenic()
        );
    }
}

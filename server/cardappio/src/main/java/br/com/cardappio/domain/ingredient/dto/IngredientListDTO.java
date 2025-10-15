package br.com.cardappio.domain.ingredient.dto;
import br.com.cardappio.domain.ingredient.Ingredient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record IngredientListDTO(
        UUID id,
        String name,
        UnityOfMeasurementDTO unityOfMeasurement,
        LocalDate expirationDate,
        BigDecimal quantity,
        boolean active
) {
    public IngredientListDTO(final Ingredient ingredient){
        this(
                ingredient.getId(),
                ingredient.getName(),
                new UnityOfMeasurementDTO(ingredient.getUnityOfMeasurement()),
                ingredient.getExpirationDate(),
                ingredient.getQuantity(),
                ingredient.getActive()
                );
    }
}

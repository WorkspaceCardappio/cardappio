package br.com.cardappio.domain.ingredient.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import br.com.cardappio.domain.ingredient.Ingredient;

public record IngredientListDTO(
        UUID id,
        String name,
        UnityOfMeasurementDTO unityOfMeasurement,
        LocalDate expirationDate,
        BigDecimal quantity,
        Boolean active,
        Boolean allergenic
) {
    public IngredientListDTO(final Ingredient ingredient){
        this(
                ingredient.getId(),
                ingredient.getName(),
                new UnityOfMeasurementDTO(ingredient.getUnityOfMeasurement()),
                ingredient.getExpirationDate(),
                ingredient.getQuantity(),
                ingredient.getActive(),
                ingredient.getAllergenic()
        );
    }
}

package br.com.cardappio.domain.product.ingredient.dto;

import br.com.cardappio.enums.UnityOfMeasurement;
import br.com.cardappio.utils.EnumDTO;

import java.util.UUID;

public record IngredientDTO(

        UUID id,
        String name,
        EnumDTO unityOfMeasurement

) {

    public IngredientDTO(final UUID id, final String name, final UnityOfMeasurement unityOfMeasurement) {
        this(
                id, name, unityOfMeasurement.toDTO()
        );
    }

}

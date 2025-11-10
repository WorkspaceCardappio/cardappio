package br.com.cardappio.domain.product.dto;

import br.com.cardappio.domain.product.ingredient.ProductIngredient;
import br.com.cardappio.utils.EnumDTO;

import java.util.UUID;

public record ProductIngredientListDTO(
        UUID id,
        String name,
        EnumDTO unityOfMeasurement
) {

    public ProductIngredientListDTO(final ProductIngredient productIngredient) {
        this(
                productIngredient.getId(),
                productIngredient.getIngredient().getName(),
                productIngredient.getIngredient().getUnityOfMeasurement().toDTO()
        );
    }

}

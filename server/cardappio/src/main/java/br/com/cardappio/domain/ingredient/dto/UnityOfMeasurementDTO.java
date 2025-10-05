package br.com.cardappio.domain.ingredient.dto;

import br.com.cardappio.enums.UnityOfMeasurement;

public record UnityOfMeasurementDTO(
        Long code,
        String description
) {
    public UnityOfMeasurementDTO (final UnityOfMeasurement unity){
        this(
                unity.getCode(),
                unity.getDescription()
        );
    }
}

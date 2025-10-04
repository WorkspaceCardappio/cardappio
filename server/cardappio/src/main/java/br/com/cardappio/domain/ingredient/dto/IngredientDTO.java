package br.com.cardappio.domain.ingredient.dto;

import br.com.cardappio.domain.ingredient.Ingredient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record IngredientDTO(
        UUID id,
        String name,
        BigDecimal quantity,
        LocalDate expirationDate,
        UnityOfMeasurementDTO unityOfMeasurement,
        Boolean active,
        Boolean allergenic
) { }


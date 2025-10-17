package br.com.cardappio.domain.restaurant.dto;

import java.util.UUID;

public record RestaurantListDTO(
        UUID id,
        String name,
        Boolean active
) {}

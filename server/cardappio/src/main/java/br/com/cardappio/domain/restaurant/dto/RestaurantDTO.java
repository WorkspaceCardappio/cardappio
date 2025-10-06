package br.com.cardappio.domain.restaurant.dto;

import java.util.UUID;
import br.com.cardappio.domain.restaurant.Restaurant;

public record RestaurantDTO(
        UUID id,
        String name,
        Boolean active,
        String cnpj
) {
    public RestaurantDTO(Restaurant restaurant) {
        this(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getActive(),
                restaurant.getCnpj()
        );
    }
}

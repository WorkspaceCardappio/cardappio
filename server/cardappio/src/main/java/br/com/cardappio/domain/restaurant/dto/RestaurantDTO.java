package br.com.cardappio.domain.restaurant.dto;

import java.util.UUID;

import br.com.cardappio.domain.address.Address;
import br.com.cardappio.domain.restaurant.Restaurant;

import static org.apache.coyote.http11.Constants.a;

public record RestaurantDTO(
        UUID id,
        String name,
        Boolean active,
        String cnpj,
        Address address
) {
    public RestaurantDTO(Restaurant restaurant) {
        this(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getActive(),
                restaurant.getCnpj(),
                restaurant.getAddress()
        );
    }
}

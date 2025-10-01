package br.com.cardappio.domain.city.dto;

import br.com.cardappio.domain.city.City;

import java.util.UUID;

public record CityListDTO(
        UUID id,

        String name,

        String address
) {
    public CityListDTO(final City city) {
        this(
                city.getId(),
                city.getName(),
                city.getAddress() != null ? city.getAddress().getStreet() : null
        );
    }
}

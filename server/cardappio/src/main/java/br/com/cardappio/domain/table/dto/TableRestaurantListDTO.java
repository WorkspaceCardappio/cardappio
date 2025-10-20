package br.com.cardappio.domain.table.dto;

import java.util.UUID;

import br.com.cardappio.domain.table.TableRestaurant;
import br.com.cardappio.utils.EnumDTO;

public record TableRestaurantListDTO(
        UUID id,
        String number,
        Long places,
        EnumDTO status
) {
    public TableRestaurantListDTO(final TableRestaurant table) {
        this(
                table.getId(),
                table.getNumber(),
                table.getPlaces(),
                table.getStatus().toDTO()
        );
    }
}

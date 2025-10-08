package br.com.cardappio.domain.table.dto;

import br.com.cardappio.domain.table.TableRestaurant;
import br.com.cardappio.enums.EnumCodigoDescricaoDTO;

import java.util.UUID;

public record TableRestaurantListDTO(
        UUID id,
        String number,
        Long places,
        EnumCodigoDescricaoDTO status
) {
    public TableRestaurantListDTO(final TableRestaurant table) {
        this(
                table.getId(),
                table.getNumber(),
                table.getPlaces(),
                new EnumCodigoDescricaoDTO(table.getStatus())
        );
    }
}

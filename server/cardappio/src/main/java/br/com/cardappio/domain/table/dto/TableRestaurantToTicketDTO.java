package br.com.cardappio.domain.table.dto;

import java.util.UUID;

import br.com.cardappio.domain.table.TableRestaurant;

public record TableRestaurantToTicketDTO(
        UUID id,
        String number
) {

    public TableRestaurantToTicketDTO(final TableRestaurant tableRestaurant) {
        this(tableRestaurant.getId(), tableRestaurant.getNumber());
    }

}

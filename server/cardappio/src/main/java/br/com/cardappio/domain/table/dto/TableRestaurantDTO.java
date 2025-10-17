package br.com.cardappio.domain.table.dto;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import br.com.cardappio.domain.table.TableRestaurant;
import br.com.cardappio.utils.EnumDTO;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TableRestaurantDTO(
        UUID id,

        @NotBlank(message = Messages.EMPTY_NUMBER)
        @Length(max = 10, message = Messages.SIZE_10)
        String number,

        EnumDTO status,

        @NotNull(message = Messages.EMPTY_NUMBER)
        @Max(value = 10, message = Messages.SIZE_10)
        Long places
) {
    public TableRestaurantDTO(final TableRestaurant table) {
        this(
                table.getId(),
                table.getNumber(),
                table.getStatus().toDTO(),
                table.getPlaces()
        );
    }
}

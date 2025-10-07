package br.com.cardappio.domain.table.dto;

import br.com.cardappio.domain.table.TableRestaurant;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record TableRestaurantDTO(
        UUID id,

        @NotBlank(message = Messages.EMPTY_NUMBER)
        @Length(max = 10, message = Messages.SIZE_10)
        String number,

        @NotNull(message = Messages.EMPTY_NUMBER)
        @Max(value = 10, message = Messages.SIZE_10)
        Long places,

        Long status
) {
    public TableRestaurantDTO(final TableRestaurant table) {
        this(
                table.getId(),
                table.getNumber(),
                table.getPlaces(),
                table.getStatus().getCode()
        );
    }
}

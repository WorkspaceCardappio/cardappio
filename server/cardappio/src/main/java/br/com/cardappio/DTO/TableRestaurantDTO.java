package br.com.cardappio.DTO;

import br.com.cardappio.entity.TableRestaurant;
import br.com.cardappio.enums.TableStatus;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.UUID;

public record TableRestaurantDTO(
        UUID id,

        @NotBlank(message = Messages.EMPTY_NUMBER)
        @Length(max=10, message = Messages.SIZE_10)
        @UniqueElements
        String number,

        TableStatus status,

        @NotNull(message = Messages.EMPTY_NUMBER)
        @Max(value = 10, message = Messages.SIZE_10)
        Long places
) {
    public TableRestaurantDTO(final TableRestaurant table){
        this(
                table.getId(),
                table.getNumber(),
                table.getStatus(),
                table.getPlaces()
        );
    }
}

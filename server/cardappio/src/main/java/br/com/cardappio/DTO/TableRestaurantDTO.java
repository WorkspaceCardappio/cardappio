package br.com.cardappio.DTO;

import br.com.cardappio.entity.TableRestaurant;
import br.com.cardappio.enums.TableStatus;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record TableRestaurantDTO(
        UUID id,

        @NotBlank(message = Messages.EMPTY_NUMBER)
        @Length(max=10, message = Messages.SIZE_10)
        String number,

        TableStatus status,

        @NotBlank(message = Messages.EMPTY_NUMBER)
        @Length(max=10, message = Messages.SIZE_10)
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

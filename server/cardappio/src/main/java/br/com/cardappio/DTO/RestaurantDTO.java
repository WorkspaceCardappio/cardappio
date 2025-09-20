package br.com.cardappio.DTO;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import br.com.cardappio.entity.Address;
import br.com.cardappio.entity.Restaurant;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotBlank;

public record RestaurantDTO(

        UUID id,
        @NotBlank(message = Messages.EMPTY_NAME)
        @Length(max = 255, message = Messages.SIZE_255)
        String name,

        boolean active,

        Address address,

        @Length(max = 14, message = "CNPJ deve ter no máximo 14 caracteres")
        String cnpj

) {
    public RestaurantDTO(final Restaurant restaurant) {
        this(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getActive(),
                restaurant.getAddress(),
                restaurant.getCnpj()
        );
    }
}
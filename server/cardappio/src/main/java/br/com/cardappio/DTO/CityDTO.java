package br.com.cardappio.DTO;

import br.com.cardappio.entity.City;
import br.com.cardappio.enums.State;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.UUID;

public record CityDTO(
        UUID id,
        @NotBlank(message = Messages.EMPTY_NAME)
        @Length(max = 255, message = Messages.SIZE_255)
        String name,
        @NotNull
        State state
) {
    public CityDTO(final City city) {
        this(
                city.getId(),
                city.getName(),
                city.getState()
        );
    }
}
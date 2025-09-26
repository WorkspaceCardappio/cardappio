package br.com.cardappio.domain.menu.dto;

import br.com.cardappio.domain.menu.Menu;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record MenuDTO(
        UUID id,
        @NotBlank(message = Messages.EMPTY_NAME)
        @Length(max = 255, message = Messages.SIZE_255)
        String name,
        boolean active,
        @Length(max = 255, message = Messages.SIZE_255)
        String note,
        @Length(max = 30, message = Messages.SIZE_10)
        String theme,
        @NotNull
        UUID restaurantId
) {
    public MenuDTO(final Menu menu) {
        this(
                menu.getId(),
                menu.getName(),
                menu.getActive(),
                menu.getNote(),
                menu.getTheme(),
                menu.getRestaurant().getId()
        );
    }
}
package br.com.cardappio.domain.menu.dto;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import br.com.cardappio.domain.menu.Menu;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MenuListDTO(

        UUID id,
        String name,
        Boolean active,
        String note,
        String theme
) {

    public MenuListDTO(final Menu menu) {

        this(
            menu.getId(),
            menu.getName(),
            menu.getActive(),
            menu.getNote(),
            menu.getTheme()
        );
    }

}

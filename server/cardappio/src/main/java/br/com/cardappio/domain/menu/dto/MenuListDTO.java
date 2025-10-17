package br.com.cardappio.domain.menu.dto;

import java.util.UUID;

import br.com.cardappio.domain.menu.Menu;

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

package br.com.cardappio.DTO;

import br.com.cardappio.entity.Menu;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record MenuDTO(

        UUID id,
        @NotBlank(message = Messages.EMPTY_NAME)
        @Length(max = 255, message = Messages.SIZE_255)
        String name,

        boolean active,

        @Length(max = 255, message = Messages.SIZE_255)
        String note

) {
    public MenuDTO(final Menu menu) {
        this(menu.getId(), menu.getName(), menu.getActive(), menu.getNote());
    }
}
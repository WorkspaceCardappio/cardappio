package br.com.cardappio.domain.menu.dto;

import java.util.List;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MenuDTO(

        UUID id,

        @NotBlank(message = Messages.EMPTY_NAME)
        @Length(max = 255, message = Messages.SIZE_255)
        String name,

        Boolean active,

        @Length(max = 255, message = Messages.SIZE_255)
        String note,

        @Length(max = 30, message = Messages.SIZE_30)
        String theme,

        @NotNull
        List<MenuProductDTO> products
) {
}

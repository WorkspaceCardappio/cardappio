package br.com.cardappio.domain.category.dto;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import br.com.cardappio.utils.IdDTO;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotBlank;

public record CategoryDTO(

        UUID id,
        @NotBlank(message = Messages.EMPTY_NAME)
        @Length(max = 255, message = Messages.SIZE_255)
        String name,

        boolean active,

        @Length(max = 255, message = Messages.SIZE_255)
        String image,

        IdDTO parent
) {}


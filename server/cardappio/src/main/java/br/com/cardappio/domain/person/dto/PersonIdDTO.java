package br.com.cardappio.domain.person.dto;

import java.util.UUID;

import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotNull;

public record PersonIdDTO(

        @NotNull(message = Messages.CODE_NOT_FOUND)
        UUID id
) {}

package br.com.cardappio.domain.city.dto;

import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record EntityIdDTO(
        @NotNull(message = Messages.CODE_NOT_FOUND)
        UUID id
) {}

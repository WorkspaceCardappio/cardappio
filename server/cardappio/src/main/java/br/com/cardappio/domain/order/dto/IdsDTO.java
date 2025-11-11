package br.com.cardappio.domain.order.dto;

import java.util.Set;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;

public record IdsDTO(

        @NotEmpty(message = "Ids não pode estar vazio.")
        Set<UUID> ids
) {
}
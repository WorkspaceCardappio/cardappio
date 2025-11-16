package br.com.cardappio.domain.order.dto;

import java.util.Set;
import java.util.UUID;

public record IdsDTO(

        Set<UUID> ids
) {
}
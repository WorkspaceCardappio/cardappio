package br.com.cardappio.domain.address.dto;

import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CityIdDTO {
    @NotNull(message = Messages.CODE_NOT_FOUND)
    UUID id;
}

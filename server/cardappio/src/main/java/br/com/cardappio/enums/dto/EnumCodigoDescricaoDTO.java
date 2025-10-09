package br.com.cardappio.enums.dto;

import br.com.cardappio.interfaces.CardappioEnum;

public record EnumCodigoDescricaoDTO(
        Long code,
        String description
) {
    public EnumCodigoDescricaoDTO(CardappioEnum cardappioEnum) {
        this(
                cardappioEnum.getCode(),
                cardappioEnum.getDescription()
        );
    }
}

package br.com.cardappio.enums;

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

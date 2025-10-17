package br.com.cardappio.enums;

import br.com.cardappio.utils.EnumDTO;

public interface EnumSerialize {

    Long getCode();

    String getDescription();

    default EnumDTO toDTO() {
        return new EnumDTO(getCode(), getDescription());
    }

}

package br.com.cardappio.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UnityOfMeasurement {
    LITER(1L, "Litro"),
    MILLILITER(2L, "Mililitro"),
    GRAM(3L, "Grama"),
    KILOGRAM(4L, "Quilograma"),
    UNIT(5L, "Unidade");

    private final Long code;
    private final String description;

    public static UnityOfMeasurement fromCode(Long code){
        for(UnityOfMeasurement value: UnityOfMeasurement.values()){
            if(value.code.equals(code)){
                return value;
            }
        } throw new IllegalArgumentException("Código inválido" + code);
    }
}



package br.com.cardappio.domain.ingredient.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UnityOfMeasurement {
    LITER(0, "Litro"),
    MILLILITER(1, "Mililitro"),
    GRAM(2, "Grama"),
    KILOGRAM(3, "Quilograma"),
    UNIT(4, "Unidade");

    private final int code;
    private final String description;

    public static UnityOfMeasurement fromCode(int code){
        for(UnityOfMeasurement value: UnityOfMeasurement.values()){
            if(value.code == code){
                return value;
            }
        } throw new IllegalArgumentException("Código inválido" + code);
    }
}



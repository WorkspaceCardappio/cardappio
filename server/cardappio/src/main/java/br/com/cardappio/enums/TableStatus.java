package br.com.cardappio.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TableStatus {
    FREE (0, "livre"),
    RESERVED (1, "reservada"),
    OCCUPIED (2, "ocupada"),
    UNAVAILABLE (3, "indisponivel");

    private final int code;
    private final String description;

    public static TableStatus fromCodigo(final int code){
        for (final TableStatus status: TableStatus.values()){
            if(status.getCode() == code){
                return status;
            }
        }
        throw new IllegalArgumentException("Código não existe");
    }
}

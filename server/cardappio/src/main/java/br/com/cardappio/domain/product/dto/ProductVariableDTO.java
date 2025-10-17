package br.com.cardappio.domain.product.dto;

import br.com.cardappio.domain.product.ProductVariable;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ProductVariableDTO(

        UUID id,

        @NotBlank
        String name
) {
    public ProductVariableDTO(final ProductVariable productVariable){
        this(
                productVariable.getId(),
                productVariable.getName()
        );
    }
}

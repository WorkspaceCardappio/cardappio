package br.com.cardappio.domain.product.dto;

import java.util.UUID;

import br.com.cardappio.domain.variable.ProductVariable;
import jakarta.validation.constraints.NotBlank;

public record ProductVariableDTO(

        UUID id,

        @NotBlank
        String name
) {
    public ProductVariableDTO(final ProductVariable productVariable) {
        this(
                productVariable.getId(),
                productVariable.getName()
        );
    }
}

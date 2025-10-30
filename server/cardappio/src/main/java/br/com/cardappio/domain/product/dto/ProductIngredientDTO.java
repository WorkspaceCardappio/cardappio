package br.com.cardappio.domain.product.dto;

import br.com.cardappio.utils.IdDTO;

import java.util.UUID;

public record ProductIngredientDTO(

        UUID id,
        
        UUID product,

        IdDTO ingredient
) {
}

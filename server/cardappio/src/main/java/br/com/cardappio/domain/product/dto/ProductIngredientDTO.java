package br.com.cardappio.domain.product.dto;

import br.com.cardappio.domain.product.Product;
import br.com.cardappio.utils.IdDTO;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductIngredientDTO(

        UUID id,

        Product product,

        IdDTO ingredient,

        BigDecimal quantity
) {
}

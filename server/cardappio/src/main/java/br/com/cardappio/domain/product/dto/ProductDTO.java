package br.com.cardappio.domain.product.dto;

import br.com.cardappio.domain.additional.dto.AdditionalDTO;
import br.com.cardappio.domain.category.Category;
import br.com.cardappio.domain.ingredient.Ingredient;
import br.com.cardappio.domain.ingredient.dto.IngredientDTO;
import br.com.cardappio.domain.product.Product;
import br.com.cardappio.utils.Messages;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ProductDTO(

        UUID id,

        @NotBlank(message = Messages.EMPTY_NAME)
        @Length(max = 255, message = Messages.SIZE_255)
        String name,

        @NotNull
        @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
        BigDecimal price,

        @NotNull
        @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
        BigDecimal quantity,

        Boolean active,

        @Future
        LocalDate expirationDate,

        @Length(max = 255, message = Messages.SIZE_255)
        String image,

        Category category,

        List<AdditionalDTO> additional,

        List<ProductVariableDTO> variables,

        List<IngredientDTO> ingredients
) {}

package br.com.cardappio.domain.product.dto;

import br.com.cardappio.domain.additional.dto.AdditionalDTO;
import br.com.cardappio.domain.category.Category;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ProductDTO(

        UUID id,

        @NotBlank(message = Messages.EMPTY_NAME)
        @Length(max = 255, message = Messages.SIZE_255)
        String name,

        Boolean active,

        @Future
        LocalDate expirationDate,

        @Length(max = 255, message = Messages.SIZE_255)
        String image,

        @NotNull
        Category category,

        List<AdditionalDTO> additional,

        List<ProductVariableDTO> variables,

        List<ProductIngredientDTO> ingredients
) {
}

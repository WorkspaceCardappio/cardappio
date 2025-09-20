package br.com.cardappio.domain.category.dto;

import br.com.cardappio.domain.category.Category;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record CategoryDTO(

        UUID id,
        @NotBlank(message = Messages.EMPTY_NAME)
        @Length(max = 255, message = Messages.SIZE_255)
        String name,

        boolean active,

        @Length(max = 255, message = Messages.SIZE_255)
        String image,

        Category subCategory
) {
    public CategoryDTO(final Category category) {
        this(
                category.getId(),
                category.getName(),
                category.getActive(),
                category.getImage(),
                category.getParent()
        );
    }
}


package br.com.cardappio.DTO;

import br.com.cardappio.entity.Category;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CategoryDTO(

        Long id,
        @NotBlank(message = "Campo nome não pode ficar vazio")
        @Length(max = 255, message = "Tamanho do nome não pode ultrapassar 255 caracteres")
        String name,

        boolean active,

        @Length(max = 255)
        String image,

        Category subCategory
) {
    public CategoryDTO(final Category category) {
        this(
                category.getId(),
                category.getName(),
                category.getActive(),
                category.getImage(),
                category.getSubCategory()
        );
    }
}


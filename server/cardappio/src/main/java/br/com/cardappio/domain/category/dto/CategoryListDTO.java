package br.com.cardappio.domain.category.dto;

import br.com.cardappio.domain.category.Category;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record CategoryListDTO(

    UUID id,
    String name,
    boolean active,
    String image,

    Category parent
)
    {   public CategoryListDTO(final Category category){
        this(
                category.getId(),
                category.getName(),
                category.getParent()
        );
    }
}

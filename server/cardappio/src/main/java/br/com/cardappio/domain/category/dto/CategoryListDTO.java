package br.com.cardappio.domain.category.dto;

import br.com.cardappio.domain.category.Category;

import java.util.Optional;
import java.util.UUID;

public record CategoryListDTO(

        UUID id,
        String name,
        boolean active,
        String image,
        String imageUrl,
        CategoryParentDTO parent
) {
    public CategoryListDTO(final Category category) {
        this(
                category.getId(),
                category.getName(),
                category.getActive(),
                category.getImage(),
                null,
                Optional.ofNullable(category.getParent())
                        .map(CategoryParentDTO::new)
                        .orElse(null)
        );
    }
}

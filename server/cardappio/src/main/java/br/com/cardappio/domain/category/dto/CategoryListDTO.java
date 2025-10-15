package br.com.cardappio.domain.category.dto;

import br.com.cardappio.domain.category.Category;

import java.util.Optional;
import java.util.UUID;

public record CategoryListDTO(

        UUID id,
        String name,
        boolean active,
        CategoryParentDTO parent
) {
    public CategoryListDTO(final Category category) {
        this(
                category.getId(),
                category.getName(),
                category.getActive(),
                Optional.ofNullable(category.getParent())
                        .map(CategoryParentDTO::new)
                        .orElse(null)
        );
    }
}

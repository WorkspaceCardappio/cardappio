package br.com.cardappio.domain.category.dto;

import br.com.cardappio.domain.category.Category;

import java.util.UUID;

public record CategoryParentDTO(
        UUID id,
        String name

) {
    public CategoryParentDTO(final Category category) {
        this(
                category.getId(),
                category.getName()
        );
    }
}

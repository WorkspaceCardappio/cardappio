package br.com.cardappio.domain.category.adapter;

import br.com.cardappio.domain.category.dto.CategoryDTO;
import br.com.cardappio.domain.category.Category;
import com.cardappio.core.adapter.Adapter;

public class CategoryAdapter implements Adapter<Category, CategoryDTO, CategoryDTO> {

    @Override
    public CategoryDTO toDTO(final Category entity) {
        return new CategoryDTO(entity);
    }

    @Override
    public Category toEntity(final CategoryDTO dto) {
        return Category.of(dto);
    }
}

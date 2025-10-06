package br.com.cardappio.domain.category.adapter;

import br.com.cardappio.domain.category.dto.CategoryDTO;
import br.com.cardappio.domain.category.Category;
import br.com.cardappio.domain.category.dto.CategoryListDTO;
import com.cardappio.core.adapter.Adapter;

public class CategoryAdapter implements Adapter<Category, CategoryListDTO, CategoryDTO> {

    @Override
    public CategoryListDTO toDTO(final Category entity) {
        return new CategoryListDTO(entity);
    }

    @Override
    public Category toEntity(final CategoryDTO dto) {
        return Category.of(dto);
    }
}

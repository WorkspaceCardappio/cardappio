package br.com.cardappio.adapter;

import br.com.cardappio.DTO.CategoryDTO;
import br.com.cardappio.entity.Category;
import com.cardappio.core.adapter.Adapter;

public class CategoryAdapter implements Adapter<CategoryDTO, Category> {

    @Override
    public CategoryDTO toDTO(Category entity) {
        return new CategoryDTO(entity);
    }

    @Override
    public Category toEntity(CategoryDTO dto) {
        return Category.of(dto);
    }
}

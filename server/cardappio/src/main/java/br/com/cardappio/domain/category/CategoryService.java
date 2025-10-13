package br.com.cardappio.domain.category;

import br.com.cardappio.domain.category.dto.CategoryDTO;
import br.com.cardappio.domain.category.adapter.CategoryAdapter;

import br.com.cardappio.domain.category.dto.CategoryListDTO;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryService extends CrudService<Category, UUID, CategoryListDTO, CategoryDTO> {

    @Override
    protected Adapter<Category, CategoryListDTO, CategoryDTO> getAdapter() {
        return new CategoryAdapter();
    }
}

package br.com.cardappio.domain.category;

import br.com.cardappio.domain.category.dto.CategoryDTO;
import br.com.cardappio.domain.category.adapter.CategoryAdapter;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryService extends CrudService<Category, UUID, CategoryDTO, CategoryDTO> {

    @Override
    protected Adapter<Category, CategoryDTO, CategoryDTO> getAdapter() {
        return new CategoryAdapter();
    }
}

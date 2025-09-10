package br.com.cardappio.service;

import br.com.cardappio.DTO.CategoryDTO;
import br.com.cardappio.adapter.CategoryAdapter;
import br.com.cardappio.entity.Category;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.repository.CrudRepository;
import com.cardappio.core.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryService extends CrudService<Category, CategoryDTO, UUID> {

    @Override
    protected Adapter<CategoryDTO, Category> getAdapter() {
        return new CategoryAdapter();
    }
}

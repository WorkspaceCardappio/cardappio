package br.com.cardappio.domain.category;

import br.com.cardappio.domain.category.adapter.CategoryAdapter;
import br.com.cardappio.domain.category.dto.CategoryDTO;
import br.com.cardappio.domain.category.dto.CategoryListDTO;
import br.com.cardappio.domain.category.dto.FlutterCategoryDTO;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService extends CrudService<Category, UUID, CategoryListDTO, CategoryDTO> {

    private final CategoryRepository repository;

    @Override
    protected Adapter<Category, CategoryListDTO, CategoryDTO> getAdapter() {
        return new CategoryAdapter();
    }

    public List<FlutterCategoryDTO> findFlutterCategories(UUID idMenu) {

        return repository.findFlutterCategories(idMenu);
    }
}

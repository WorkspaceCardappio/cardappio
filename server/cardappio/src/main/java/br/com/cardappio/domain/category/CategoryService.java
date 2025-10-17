package br.com.cardappio.domain.category;

import br.com.cardappio.components.S3StorageService;
import br.com.cardappio.domain.category.adapter.CategoryAdapter;
import br.com.cardappio.domain.category.dto.CategoryDTO;
import br.com.cardappio.domain.category.dto.CategoryListDTO;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService extends CrudService<Category, UUID, CategoryListDTO, CategoryDTO> {

    private final S3StorageService s3StorageService;

    @Override
    protected Adapter<Category, CategoryListDTO, CategoryDTO> getAdapter() {
        return new CategoryAdapter();
    }

//    @Override
//    protected void beforeSave(Category category) {
//
//        s3StorageService.saveFile(category.getArchive());
//    }
}

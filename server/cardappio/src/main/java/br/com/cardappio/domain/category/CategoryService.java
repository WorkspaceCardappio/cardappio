package br.com.cardappio.domain.category;

import br.com.cardappio.components.s3.S3StorageComponent;
import br.com.cardappio.domain.category.adapter.CategoryAdapter;
import br.com.cardappio.domain.category.dto.CategoryDTO;
import br.com.cardappio.domain.category.dto.CategoryListDTO;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService extends CrudService<Category, UUID, CategoryListDTO, CategoryDTO> {

    private final S3StorageComponent s3StorageComponent;
    private final CategoryRepository repository;

    @Override
    protected Adapter<Category, CategoryListDTO, CategoryDTO> getAdapter() {
        return new CategoryAdapter();
    }

    public void saveCategory(MultipartFile file, CategoryDTO dto) {

        if (Objects.nonNull(file)) {

            dto.setImage(s3StorageComponent.getKey(file));

            s3StorageComponent.saveFile(file, dto.getImage());
        }

        create(dto);
    }

    public void updateCategory(UUID id, MultipartFile file, CategoryDTO dto) {

        if (Objects.nonNull(file)) {

            String oldImage = repository.findImageById(id);

            s3StorageComponent.saveFile(file, oldImage);

            dto.setImage(s3StorageComponent.getKey(file));
        }

        update(id, dto);

    }
}

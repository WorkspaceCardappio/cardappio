package br.com.cardappio.domain.category;

import br.com.cardappio.components.s3.S3StorageComponent;
import br.com.cardappio.domain.category.adapter.CategoryAdapter;
import br.com.cardappio.domain.category.dto.CategoryDTO;
import br.com.cardappio.domain.category.dto.CategoryListDTO;
import br.com.cardappio.domain.category.dto.FlutterCategoryDTO;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService extends CrudService<Category, UUID, CategoryListDTO, CategoryDTO> {

    private final S3StorageComponent s3StorageComponent;
    private final CategoryRepository repository;
    private final CategoryAdapter categoryAdapter;

    @Override
    protected Adapter<Category, CategoryListDTO, CategoryDTO> getAdapter() {
        return categoryAdapter;
    }

    public List<FlutterCategoryDTO> findFlutterCategories(UUID idMenu) {
        List<Category> categories = repository.findFlutterCategoriesEntities(idMenu);

        return categories.stream()
                .map(category -> {
                    String imageUrl = s3StorageComponent.generatePresignedUrl(category.getImage());
                    return new FlutterCategoryDTO(
                            category.getId(),
                            category.getName(),
                            category.getImage(),
                            imageUrl
                    );
                })
                .collect(Collectors.toList());
    }

    public void saveCategory(MultipartFile file, CategoryDTO dto) {

        if (Objects.nonNull(file)) {

            String key = s3StorageComponent.getKey(file);
            dto.setImage(key);

            s3StorageComponent.saveFile(file, key, null);
        }

        create(dto);
    }

    public void updateCategory(UUID id, MultipartFile file, CategoryDTO dto) {

        if (Objects.nonNull(file)) {

            String oldImage = repository.findImageById(id);
            String newKey = s3StorageComponent.getKey(file);

            s3StorageComponent.saveFile(file, newKey, oldImage);

            dto.setImage(newKey);
        }

        update(id, dto);

    }

    @Override
    protected void beforeDelete(Category category) {

        String image = category.getImage();

        if (Objects.nonNull(image)) {

            s3StorageComponent.deleteMatchingObject(image);
        }
    }
}
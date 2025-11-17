package br.com.cardappio.domain.category.adapter;

import br.com.cardappio.components.s3.S3StorageComponent;
import br.com.cardappio.domain.category.dto.CategoryDTO;
import br.com.cardappio.domain.category.Category;
import br.com.cardappio.domain.category.dto.CategoryListDTO;
import com.cardappio.core.adapter.Adapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryAdapter implements Adapter<Category, CategoryListDTO, CategoryDTO> {

    private final S3StorageComponent s3StorageComponent;

    @Override
    public CategoryListDTO toDTO(final Category entity) {
        CategoryListDTO dto = new CategoryListDTO(entity);

        String imageUrl = s3StorageComponent.generatePresignedUrl(entity.getImage());

        return new CategoryListDTO(
            dto.id(),
            dto.name(),
            dto.active(),
            dto.image(),
            imageUrl,
            dto.parent()
        );
    }

    @Override
    public Category toEntity(final CategoryDTO dto) {
        return Category.of(dto);
    }
}

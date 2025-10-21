package br.com.cardappio.domain.category;

import br.com.cardappio.domain.category.dto.CategoryDTO;
import br.com.cardappio.domain.category.dto.CategoryListDTO;
import com.cardappio.core.controller.CrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController extends CrudController<Category, UUID, CategoryListDTO, CategoryDTO> {

    private final CategoryService categoryService;
    
    @PostMapping(path = "with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> withImage(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("dto") CategoryDTO dto
    ) {

        categoryService.saveCategory(file, dto);

        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{id}/with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> withImage(
            @PathVariable UUID id,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("dto") CategoryDTO dto
    ) {

        categoryService.updateCategory(id, file, dto);

        return ResponseEntity.ok().build();
    }
}

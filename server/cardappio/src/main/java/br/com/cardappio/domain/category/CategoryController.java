package br.com.cardappio.domain.category;

import br.com.cardappio.components.S3StorageService;
import br.com.cardappio.domain.category.dto.CategoryDTO;
import br.com.cardappio.domain.category.dto.CategoryListDTO;
import com.cardappio.core.controller.CrudController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController extends CrudController<Category, UUID, CategoryListDTO, CategoryDTO> {

    private final S3StorageService s3StorageService;

    @Override
    protected ResponseEntity<Void> create(@Valid CategoryDTO newDTO) {

//        s3StorageService.saveFile(newDTO.archive());

        return ResponseEntity.ok().build();

    }
}

package br.com.cardappio.domain.category;

import br.com.cardappio.domain.category.dto.CategoryDTO;
import br.com.cardappio.domain.category.dto.CategoryListDTO;
import br.com.cardappio.domain.category.dto.FlutterCategoryDTO;
import com.cardappio.core.controller.CrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController extends CrudController<Category, UUID, CategoryListDTO, CategoryDTO> {

    private final CategoryService service;

    @GetMapping("/{idMenu}/flutter-categories")
    ResponseEntity<List<FlutterCategoryDTO>> findFlutterCategories(@PathVariable UUID idMenu) {
        return ResponseEntity.ok(service.findFlutterCategories(idMenu));
    }
}

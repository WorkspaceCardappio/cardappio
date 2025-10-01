package br.com.cardappio.domain.category;

import br.com.cardappio.domain.category.dto.CategoryDTO;

import com.cardappio.core.controller.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController extends CrudController<Category, UUID, CategoryDTO, CategoryDTO> {
}

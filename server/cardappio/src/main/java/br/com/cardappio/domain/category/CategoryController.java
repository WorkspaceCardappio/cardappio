package br.com.cardappio.domain.category;

import br.com.cardappio.domain.category.dto.CategoryDTO;
import br.com.cardappio.domain.category.dto.CategoryListDTO;
import com.cardappio.core.controller.CrudController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/categories")
public class CategoryController extends CrudController<Category, UUID, CategoryListDTO, CategoryDTO> {
}

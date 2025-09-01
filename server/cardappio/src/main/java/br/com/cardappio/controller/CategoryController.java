package br.com.cardappio.controller;

import br.com.cardappio.DTO.CategoryDTO;
import br.com.cardappio.entity.Category;
import com.cardappio.core.controller.CrudController;
import com.cardappio.core.service.CrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/category")
public class CategoryController extends CrudController<Category, CategoryDTO, UUID> {

    public CategoryController(final CrudService<Category, CategoryDTO, UUID> service) {
        super(service);
    }

}

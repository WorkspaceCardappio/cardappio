package br.com.cardappio.domain.ingredient;

import br.com.cardappio.domain.ingredient.dto.IngredientDTO;

import com.cardappio.core.controller.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/ingredients")
public class IngredientController extends CrudController<Ingredient, UUID, IngredientDTO, IngredientDTO> {
}

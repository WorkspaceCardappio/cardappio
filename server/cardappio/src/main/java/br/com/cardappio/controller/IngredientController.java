package br.com.cardappio.controller;

import br.com.cardappio.DTO.IngredientDTO;
import br.com.cardappio.entity.Ingredient;
import com.cardappio.core.controller.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/ingredients")
public class IngredientController extends CrudController<Ingredient, IngredientDTO, UUID> {
}

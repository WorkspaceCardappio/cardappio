package br.com.cardappio.domain.ingredient;

import br.com.cardappio.domain.ingredient.dto.IngredientDTO;

import br.com.cardappio.domain.ingredient.dto.IngredientListDTO;
import br.com.cardappio.domain.ingredient.dto.UnityOfMeasurementDTO;
import com.cardappio.core.controller.CrudController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/ingredients")
public class IngredientController extends CrudController<Ingredient, UUID, IngredientListDTO, IngredientDTO> {

    private final IngredientService ingredientService;

    public IngredientController(final IngredientService ingredientService){
        this.ingredientService = ingredientService;
    }
    @GetMapping("/unity-of-measurement")
    public List<UnityOfMeasurementDTO> getUnitOfMeasurement(){
        return ingredientService.getUnityOfMeasurement();
    }

}

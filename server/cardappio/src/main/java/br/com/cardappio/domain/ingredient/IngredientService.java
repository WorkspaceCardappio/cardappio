package br.com.cardappio.domain.ingredient;

import br.com.cardappio.domain.ingredient.adapter.IngredientAdapter;
import br.com.cardappio.domain.ingredient.dto.IngredientDTO;
import br.com.cardappio.domain.ingredient.dto.IngredientListDTO;
import br.com.cardappio.domain.ingredient.dto.UnityOfMeasurementDTO;
import br.com.cardappio.enums.UnityOfMeasurement;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class IngredientService extends CrudService<Ingredient, UUID, IngredientListDTO, IngredientDTO> {

    @Override
    protected Adapter<Ingredient, IngredientListDTO, IngredientDTO> getAdapter() {
        return new IngredientAdapter();
    }

    public List<UnityOfMeasurementDTO> getUnityOfMeasurement() {
        return Arrays.stream(UnityOfMeasurement.values())
                .map(a -> new UnityOfMeasurementDTO(a.getCode(), a.getDescription()))
                .toList();
    }
}

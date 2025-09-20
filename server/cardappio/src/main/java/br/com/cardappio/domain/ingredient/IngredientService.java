package br.com.cardappio.domain.ingredient;

import br.com.cardappio.domain.ingredient.dto.IngredientDTO;
import br.com.cardappio.domain.ingredient.adapter.IngredientAdapter;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IngredientService extends CrudService<Ingredient, UUID, IngredientDTO, IngredientDTO> {

    @Override
    protected Adapter<Ingredient, IngredientDTO, IngredientDTO> getAdapter() {

        return new IngredientAdapter();
    }
}

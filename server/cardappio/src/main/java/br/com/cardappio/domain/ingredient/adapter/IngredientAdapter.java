package br.com.cardappio.domain.ingredient.adapter;

import br.com.cardappio.domain.ingredient.dto.IngredientDTO;
import br.com.cardappio.domain.ingredient.Ingredient;
import br.com.cardappio.domain.ingredient.dto.IngredientListDTO;
import com.cardappio.core.adapter.Adapter;

public class IngredientAdapter implements Adapter<Ingredient, IngredientListDTO, IngredientDTO> {

    @Override
    public IngredientListDTO toDTO(final Ingredient entity) {
        return new IngredientListDTO(entity);
    }

    @Override
    public Ingredient toEntity(final IngredientDTO dto) {
        return Ingredient.of(dto);
    }
}

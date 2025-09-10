package br.com.cardappio.adapter;

import br.com.cardappio.DTO.IngredientDTO;
import br.com.cardappio.entity.Ingredient;
import com.cardappio.core.adapter.Adapter;

public class IngredientAdapter implements Adapter<IngredientDTO, Ingredient> {

    @Override
    public IngredientDTO toDTO(final Ingredient entity) {
        return new IngredientDTO(entity);
    }

    @Override
    public Ingredient toEntity(final IngredientDTO dto) {
        return Ingredient.of(dto);
    }
}

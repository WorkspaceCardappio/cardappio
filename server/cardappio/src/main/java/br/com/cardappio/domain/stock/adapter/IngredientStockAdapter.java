package br.com.cardappio.domain.stock.adapter;

import br.com.cardappio.domain.ingredient.IngredientStock;
import br.com.cardappio.domain.ingredient.dto.IngredientStockDTO;
import br.com.cardappio.domain.stock.dto.IngredientStockListDTO;
import com.cardappio.core.adapter.Adapter;

public class IngredientStockAdapter implements Adapter<IngredientStock, IngredientStockListDTO,IngredientStockDTO> {

    @Override
    public IngredientStockListDTO toDTO(final IngredientStock entity) {
        return new IngredientStockListDTO(entity);
    }

    @Override
    public IngredientStock toEntity(IngredientStockDTO dto) {
        return IngredientStock.of(dto);
    }

}

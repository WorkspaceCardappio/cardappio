package br.com.cardappio.domain.stock;

import br.com.cardappio.domain.ingredient.IngredientStock;
import br.com.cardappio.domain.ingredient.dto.IngredientStockDTO;
import br.com.cardappio.domain.stock.adapter.IngredientStockAdapter;
import br.com.cardappio.domain.stock.dto.IngredientStockListDTO;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IngredientStockService extends CrudService<IngredientStock, UUID, IngredientStockListDTO, IngredientStockDTO> {

    @Override
    protected Adapter<IngredientStock, IngredientStockListDTO, IngredientStockDTO> getAdapter() {
        return new IngredientStockAdapter();
    }
}

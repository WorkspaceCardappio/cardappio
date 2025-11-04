package br.com.cardappio.domain.stock;

import br.com.cardappio.domain.ingredient.IngredientStock;
import br.com.cardappio.domain.ingredient.dto.IngredientStockDTO;
import br.com.cardappio.domain.stock.dto.IngredientStockListDTO;
import com.cardappio.core.controller.CrudController;
import com.cardappio.core.service.CrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/stocks")
public class IngredientStockController extends CrudController<IngredientStock, UUID, IngredientStockListDTO, IngredientStockDTO> {

    public IngredientStockController(final CrudService<IngredientStock, UUID, IngredientStockListDTO, IngredientStockDTO> service) {
        this.setService(service);
    }

}

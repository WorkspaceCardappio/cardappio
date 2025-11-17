package br.com.cardappio.domain.stock;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardappio.core.controller.CrudController;

import br.com.cardappio.domain.ingredient.IngredientStock;
import br.com.cardappio.domain.ingredient.dto.IngredientStockDTO;
import br.com.cardappio.domain.stock.dto.IngredientStockListDTO;

@RestController
@RequestMapping("/api/stocks")
public class IngredientStockController extends CrudController<IngredientStock, UUID, IngredientStockListDTO, IngredientStockDTO> {
}
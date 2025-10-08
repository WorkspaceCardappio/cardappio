package br.com.cardappio.domain.table;

import br.com.cardappio.domain.table.dto.TableRestaurantInsertDTO;
import br.com.cardappio.domain.table.dto.TableRestaurantListDTO;
import com.cardappio.core.controller.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/tables-restaurant")
public class TableRestaurantController extends CrudController<TableRestaurant, UUID, TableRestaurantListDTO, TableRestaurantInsertDTO> {
}

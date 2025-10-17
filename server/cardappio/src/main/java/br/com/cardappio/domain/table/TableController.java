package br.com.cardappio.domain.table;

import br.com.cardappio.domain.table.dto.TableRestaurantDTO;

import com.cardappio.core.controller.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/tables")
public class TableController extends CrudController<TableRestaurant, UUID, TableRestaurantDTO, TableRestaurantDTO> {
}

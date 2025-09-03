package br.com.cardappio.controller;

import br.com.cardappio.DTO.TableRestaurantDTO;
import br.com.cardappio.entity.TableRestaurant;
import com.cardappio.core.controller.CrudController;
import com.cardappio.core.service.CrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/tables")
public class TableController extends CrudController<TableRestaurant, TableRestaurantDTO, UUID> {
    public TableController(final CrudService<TableRestaurant, TableRestaurantDTO, UUID> service) {
        super(service);
    }
}

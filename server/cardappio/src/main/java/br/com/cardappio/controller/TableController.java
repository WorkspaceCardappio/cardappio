package br.com.cardappio.controller;

import br.com.cardappio.DTO.TableRestaurantDTO;
import br.com.cardappio.entity.TableRestaurant;
import com.cardappio.core.controller.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/tables")
public class TableController extends CrudController<TableRestaurant, UUID, TableRestaurantDTO, TableRestaurantDTO> {
}

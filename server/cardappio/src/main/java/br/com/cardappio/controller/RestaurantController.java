package br.com.cardappio.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardappio.core.controller.CrudController;

import br.com.cardappio.DTO.MenuDTO;
import br.com.cardappio.DTO.RestaurantDTO;
import br.com.cardappio.entity.Menu;
import br.com.cardappio.entity.Restaurant;

@RestController
@RequestMapping("/restaurants")
@CrossOrigin(origins = "http://localhost:4200")
public class RestaurantController extends CrudController<Restaurant, UUID, RestaurantDTO, RestaurantDTO> {

}

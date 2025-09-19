package br.com.cardappio.controller;

import br.com.cardappio.DTO.MenuDTO;
import br.com.cardappio.entity.Menu;
import com.cardappio.core.controller.CrudController;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/menus")
@CrossOrigin(origins = "http://localhost:4200")
public class MenuController extends CrudController<Menu, UUID, MenuDTO, MenuDTO> {

}

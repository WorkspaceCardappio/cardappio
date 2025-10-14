package br.com.cardappio.domain.menu;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardappio.core.controller.CrudController;

import br.com.cardappio.domain.menu.dto.MenuDTO;

@RestController
@RequestMapping("/menus")
public class MenuController extends CrudController<Menu, UUID, MenuDTO, MenuDTO> {

}

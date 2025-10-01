package br.com.cardappio.domain.menu;

import br.com.cardappio.domain.menu.dto.MenuDTO;

import com.cardappio.core.controller.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/menus")
public class MenuController extends CrudController<Menu, UUID, MenuDTO, MenuDTO> {

}

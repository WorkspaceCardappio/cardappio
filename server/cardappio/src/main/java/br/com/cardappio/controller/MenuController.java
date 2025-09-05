package br.com.cardappio.controller;

import br.com.cardappio.DTO.MenuDTO;
import br.com.cardappio.entity.Menu;
import com.cardappio.core.controller.CrudController;
import com.cardappio.core.service.CrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/menu")
public class MenuController extends CrudController<Menu, MenuDTO, UUID> {

    public MenuController (final CrudController<Menu, MenuDTO, UUID> service){
        super(service);
    }
}

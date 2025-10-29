package br.com.cardappio.domain.menu;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardappio.core.controller.CrudController;

import br.com.cardappio.domain.menu.dto.MenuDTO;
import br.com.cardappio.domain.menu.dto.MenuListDTO;
import br.com.cardappio.domain.menu.dto.MenuProductListDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/menus")
public class MenuController extends CrudController<Menu, UUID, MenuListDTO, MenuDTO> {

    private final MenuService service;

    @GetMapping("/{id}/products")
    public List<MenuProductListDTO> findProductsInMenu(@PathVariable final UUID id) {
        return service.findProductsInMenu(id);
    }

}

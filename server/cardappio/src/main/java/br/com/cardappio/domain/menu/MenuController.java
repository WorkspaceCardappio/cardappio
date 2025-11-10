package br.com.cardappio.domain.menu;

import br.com.cardappio.domain.menu.dto.MenuDTO;
import br.com.cardappio.domain.menu.dto.MenuListDTO;
import br.com.cardappio.domain.menu.dto.MenuProductListDTO;
import com.cardappio.core.controller.CrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
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

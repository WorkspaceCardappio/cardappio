package br.com.cardappio.domain.menu;

<<<<<<< Updated upstream
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cardappio.core.controller.CrudController;

import br.com.cardappio.domain.menu.dto.MenuDTO;
import br.com.cardappio.domain.menu.dto.MenuListDTO;
import br.com.cardappio.domain.menu.dto.MenuProductListDTO;
import lombok.RequiredArgsConstructor;


@RequestMapping("api/menus")
@RequiredArgsConstructor
public class MenuController extends CrudController<Menu, UUID, MenuListDTO, MenuDTO> {

    private final MenuService service;

    @GetMapping("/{id}/products")
    public List<MenuProductListDTO> findProductsInMenu(@PathVariable final UUID id) {
        return service.findProductsInMenu(id);
    }
=======
import br.com.cardappio.domain.menu.dto.FlutterMenuDTO;
import br.com.cardappio.domain.menu.dto.MenuDTO;
import com.cardappio.core.controller.CrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController extends CrudController<Menu, UUID, MenuDTO, MenuDTO> {
>>>>>>> Stashed changes

    private final MenuService service;

    @GetMapping("/actives")
    ResponseEntity<List<FlutterMenuDTO>> getFlutterMenus() {


    }


}
